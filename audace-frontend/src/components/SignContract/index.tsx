import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { Card, ListGroup, ListGroupItem, Container, Row, Col, Button } from 'react-bootstrap';
import { Contract } from '../../model/contract';
import { getContractByIdAsManager, getContractByIdAsStudent} from '../../services/contractService';
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
            if (UserType === 'manager') {
                getContractByIdAsManager(parseInt(id))
                    .then((response) => {
                        console.log("Fetched contract:", response.data);
                        setContract(response.data);
                    })
                    .catch((error) => {
                        console.error("Error fetching contract as manager:", error);
                    });
            }
            if (UserType === 'student') {
                getContractByIdAsStudent(parseInt(id))
                    .then((response) => {
                        console.log("Fetched contract:", response.data);
                        setContract(response.data);
                    })
                    .catch((error) => {
                        console.error("Error fetching contract as student:", error);
                    });
            }
        }

    }, [id]);

    const { supervisor, application, startHour, endHour, totalHoursPerWeek, salary } = contract;
    const { offer } = application || {};
    const { employer } = offer || {};
    const { student } = application.cv || {};

    function handleSign(role: string) {
        switch (role) {
            case 'manager':
                signAsManager();
                break;
            case 'employer':
                console.log('Signing as employer');
                break;
            case 'student':
                console.log('Signing as student');
                break;
            default:
                console.log('Invalid role');
        }
    }

    function signAsManager() {
        const userId = parseInt(getUserId() || '0');
        if (!userId) {
            console.error("Invalid user ID");
            return;
        }
        ManagerSignContract(userId, contract?.id!)
            .then(() => {
                console.log('Manager signed the contract');
            })
            .catch((error: any) => {
                console.error('Error signing contract as manager:', error);
            });
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
                        <Card.Header as="h5">{t('signature.title')}</Card.Header>
                        <Card.Body>
                            <ListGroup>
                                <ListGroupItem className="d-flex justify-content-between align-items-center">
                                    {t('signature.manager')}
                                    <div>
                                        <Button variant="secondary" onClick={() => handleSign('manager')} disabled={UserType !== 'manager'}>
                                            {t('signature.sign')}
                                        </Button>
                                    </div>
                                </ListGroupItem>
                                <ListGroupItem className="d-flex justify-content-between align-items-center">
                                    {t('signature.employer')}
                                    <div>
                                        <Button variant="secondary" onClick={() => handleSign('employer')} disabled={UserType !== 'employer'}>
                                            {t('signature.sign')}
                                        </Button>
                                    </div>
                                </ListGroupItem>
                                <ListGroupItem className="d-flex justify-content-between align-items-center">
                                    {t('signature.student')}
                                    <div>
                                        <Button variant="secondary" onClick={() => handleSign('student')} disabled={UserType !== 'student'}>
                                            {t('signature.sign')}
                                        </Button>
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
