import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { Card, ListGroup, ListGroupItem, Container, Row, Col, Button } from 'react-bootstrap';
import { Contract } from '../../model/contract';
import { getContractById } from '../../services/contractService';
import { UserType } from "../../model/user";
import { getUserId } from '../../services/authService';
import { getUserById } from '../../services/userService';
import { ManagerSignContract } from '../../services/contractService';
import { useTranslation } from 'react-i18next';

const SignContract = () => {
    const { id } = useParams();
    const [contract, setContract] = useState<Contract | null>(null);
    const [UserType, setUserType] = useState<string | null>(null);
    const { t } = useTranslation();

    useEffect(() => {

        (async () => {
            try {
                const userId = parseInt(getUserId() || '0');
                if (!userId) {
                    console.error("Invalid user ID");
                    return;
                }

                const response = await getUserById(userId);
                setUserType(response.data.type || null);
            } catch (error) {
                console.error("Error fetching user:", error);
            }
        })();

        if (id) {
            getContractById(parseInt(id))
                .then((response) => {
                    console.log("Fetched contract:", response.data);
                    setContract(response.data);
                })
                .catch((error) => {
                    console.error("Error fetching contract:", error);
                });
        }

    }, [id]);

    if (!contract) {
        return <p>Loading contract...</p>;
    }



    const { supervisor, application, startHour, endHour, totalHoursPerWeek, salary } = contract;
    const { offer } = application || {};
    const { employer } = offer || {};
    const { student } = application.cv || {};

    function handleSign(arg0: string) {
        if (arg0 === 'manager') {
            ManagerSignContract(parseInt(getUserId() || '0') || 0, contract?.id!);
            console.log(contract);
        } else if (arg0 === 'employer') {
            console.log('employer');
        } else if (arg0 === 'student') {
            console.log('student');
        }
    }

    return (
        <Container className="mt-4">
            <Row className="justify-content-center">
                <Col md={8}>
                    <Card className="mb-3">
                        <Card.Header as="h5">{t('contractsList.singleTitle')}</Card.Header>
                        <Card.Body>
                            <ListGroup variant="flush">
                                <ListGroupItem><strong>{t('manager.createContract.startHour')}:</strong> {startHour}</ListGroupItem>
                                <ListGroupItem><strong>{t('manager.createContract.endHour')}:</strong> {endHour}</ListGroupItem>
                                <ListGroupItem><strong>{t('manager.createContract.totalHoursPerWeek')}:</strong> {totalHoursPerWeek}</ListGroupItem>
                                <ListGroupItem><strong>{t('manager.createContract.salary')}:</strong> {salary}</ListGroupItem>
                            </ListGroup>
                        </Card.Body>
                    </Card>

                    <Card className="mb-3">
                        <Card.Header as="h5">{t('infoCard.supervisor.title')}</Card.Header>
                        <Card.Body>
                            <ListGroup variant="flush">
                                <ListGroupItem><strong>{t('infoCard.supervisor.name')}:</strong> {`${supervisor.firstName} ${supervisor.lastName}`}</ListGroupItem>
                                <ListGroupItem><strong>{t('infoCard.supervisor.position')}:</strong> {supervisor.position}</ListGroupItem>
                                <ListGroupItem><strong>{t('infoCard.supervisor.email')}:</strong> {supervisor.email}</ListGroupItem>
                                <ListGroupItem><strong>{t('infoCard.supervisor.phone')}:</strong> {supervisor.phone}</ListGroupItem>
                            </ListGroup>
                        </Card.Body>
                    </Card>

                    {student && (
                        <Card className="mb-3">
                            <Card.Header as="h5">{t('infoCard.student.title')}</Card.Header>
                            <Card.Body>
                                <ListGroup variant="flush">
                                    <ListGroupItem><strong>{t('infoCard.student.name')} :</strong> {`${student.firstName} ${student.lastName}`}</ListGroupItem>
                                    <ListGroupItem><strong>{t('signup.studentId')}:</strong> {student.studentNumber}</ListGroupItem>
                                    <ListGroupItem><strong>{t('infoCard.student.email')}:</strong> {student.email}</ListGroupItem>
                                    <ListGroupItem><strong>{t('infoCard.student.phone')}:</strong> {student.phone}</ListGroupItem>
                                </ListGroup>
                            </Card.Body>
                        </Card>
                    )}

                    {employer && (
                        <Card className="mb-3">
                            <Card.Header as="h5">{t('infoCard.employer.title')}</Card.Header>
                            <Card.Body>
                                <ListGroup variant="flush">
                                    <ListGroupItem><strong>{t('infoCard.employer.name')} :</strong> {`${employer.firstName} ${employer.lastName}`}</ListGroupItem>
                                    <ListGroupItem><strong>{t('signup.companyNameEntry')}:</strong> {employer.organisation}</ListGroupItem>
                                </ListGroup>
                            </Card.Body>
                        </Card>
                    )}

                    {offer && (
                        <Card>
                            <Card.Header as="h5">{t('offersList.title')}</Card.Header>
                            <Card.Body>
                                <ListGroup variant="flush">
                                    <ListGroupItem><strong>{t('addOffer.title')}:</strong> {offer.title}</ListGroupItem>
                                    <ListGroupItem><strong>{t('addOffer.description')}:</strong> {offer.description}</ListGroupItem>
                                    <ListGroupItem><strong>{t('addOffer.startDate')}:</strong> {offer.internshipStartDate.toString()}</ListGroupItem>
                                    <ListGroupItem><strong>{t('addOffer.endDate')}:</strong> {offer.internshipEndDate.toString()}</ListGroupItem>
                                </ListGroup>
                            </Card.Body>
                        </Card>
                    )}

                    <Card className="mt-3">
                        <Card.Header as="h5">Signature</Card.Header>
                        <Card.Body>
                            <ListGroup>
                                <ListGroupItem className="d-flex justify-content-between align-items-center">
                                   {t('signature.manager')}
                                    <div>
                                        {UserType === 'manager' && !contract.managerSignature && (
                                            <Button variant="secondary" onClick={() => handleSign('manager')}>
                                                {t('signature.sign')}
                                            </Button>
                                        )}
                                        <span className="ms-2">
                                            {contract.managerSignature
                                                ? `${t('signature.signedOn')}: ${contract.managerSignature.signatureDate}`
                                                : `${t('signature.notSigned')}`}
                                        </span>
                                    </div>
                                </ListGroupItem>
                                <ListGroupItem className="d-flex justify-content-between align-items-center">
                                {t('signature.employer')}
                                    <div>
                                        {UserType === 'employer' && !contract.employerSignature && (
                                            <Button variant="secondary" onClick={() => handleSign('employer')}>
                                               {t('signature.sign')}
                                            </Button>
                                        )}
                                        <span className="ms-2">
                                            {contract.employerSignature
                                                ? `${t('signature.signedOn')}: ${contract.employerSignature.signatureDate}`
                                                : `${t('signature.notSigned')}`}
                                        </span>
                                    </div>
                                </ListGroupItem>
                                <ListGroupItem className="d-flex justify-content-between align-items-center">
                                {t('signature.student')}
                                    <div>
                                        {UserType === 'student' && !contract.studentSignature && (
                                            <Button variant="secondary" onClick={() => handleSign('student')}>
                                                {t('signature.sign')}
                                            </Button>
                                        )}
                                        <span className="ms-2">
                                            {contract.studentSignature
                                                ? `${t('signature.signedOn')}: ${contract.studentSignature.signatureDate}`
                                                : `${t('signature.notSigned')}`}
                                        </span>
                                    </div>
                                </ListGroupItem>
                            </ListGroup>
                        </Card.Body>
                    </Card>

                </Col>
            </Row>
        </Container>
    );
};

export default SignContract;
