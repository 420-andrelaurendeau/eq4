import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { Badge, Button, Card, Col, Container, ListGroup, ListGroupItem, Placeholder, Row } from 'react-bootstrap';
import { Contract, Signature } from '../../model/contract';
import { getContractById, getSignaturesByContractId, signContract } from '../../services/contractService';
import { getAuthorities, getUserId } from '../../services/authService';
import { useTranslation } from 'react-i18next';
import { Authority } from '../../model/auth';

const SignContract = () => {
  const { id } = useParams();
  const [contract, setContract] = useState<Contract>();
  const [signatures, setSignatures] = useState<Signature[]>([]);
  const { t } = useTranslation();
  const userId = getUserId();
  const userType = getAuthorities()?.[0];

  useEffect(() => {
    const fetchSignatures = async (contractId: number) => {
      getSignaturesByContractId(contractId, userType!)
        .then((response) => {
          setSignatures(response.data);
        })
        .catch((error) => {
          console.error("Error fetching signatures:", error);
        });
    };

    const fetchContract = async (contractId: number) => {
      getContractById(contractId, userType!)
        .then((response) => {
          setContract(response.data);
        })
        .catch((error) => {
          console.error("Error fetching contract:", error);
        });
    };

    if (id && userType) {
      const contractId = parseInt(id);
      fetchContract(contractId);
      fetchSignatures(contractId);
    }
  }, [id, userType]);

  const handleSign = async () => {
    if (contract && userId && userType) {
      signContract(contract.id!, parseInt(userId), userType)
        .then((response) => {
          const newSignature = response.data;
          setSignatures([...signatures, newSignature]);
        })
        .catch((error) => {
          console.error("Error signing contract:", error);
        });
    }
  };

  const getSignatoryType = (signatoryType: string) => {
    switch (signatoryType.toUpperCase()) {
      case 'EMPLOYER':
        return t('signature.employerNotSigned');
      case 'STUDENT':
        return t('signature.studentNotSigned');
      case 'MANAGER':
        return t('signature.managerNotSigned');
      default:
        return '';
    }
  }

  const getMissingSignatures = () => {
    if (!contract) return [];

    const signatoryTypes = ['Employer', 'Manager', 'Student',];
    signatoryTypes.sort((a, b) => a.toUpperCase() === userType?.toUpperCase() ? 1 : -1);
    const signaturesTypes = signatures.map(signature => signature.signatoryType);

    return signatoryTypes.filter(signatoryType => !signaturesTypes.includes(signatoryType));
  }

  const didEmployerAndStudentSign = () => {
    if (!contract) return false;
    if (userType !== Authority.MANAGER) return true;

    const signatoryTypes = ['Employer', 'Student'];
    const signaturesTypes = signatures.map(signature => signature.signatoryType);

    return signatoryTypes.every(signatoryType => signaturesTypes.includes(signatoryType));
  }

  return (
    <Container className="mt-4">
      <Row className="justify-content-center pb-5">
        <Col md={8}>
          <Card className="mb-3">
            <Card.Header as="h5">
              {contract && contract.application!.offer ? contract.application!.offer.title : <Placeholder xs={6} />}
            </Card.Header>
            <Card.Body>
              {contract && contract.application!.offer ? (
                <ListGroup variant="flush">
                  <ListGroupItem><strong>{t('signup.companyNameEntry')}:</strong> {contract!.application!.offer!.employer.organisation}</ListGroupItem>
                  <ListGroupItem><strong>{t('addOffer.startDate')}:</strong> {new Date(contract.application!.offer.internshipStartDate).toLocaleDateString()}</ListGroupItem>
                  <ListGroupItem><strong>{t('addOffer.endDate')}:</strong> {new Date(contract.application!.offer.internshipEndDate).toLocaleDateString()}</ListGroupItem>
                  <ListGroupItem><strong>{t('addOffer.description')}:</strong> {contract.application!.offer.description}</ListGroupItem>
                </ListGroup>
              ) : (
                <>
                  <Placeholder as={Card.Title} animation="glow">
                    <Placeholder xs={6} />
                  </Placeholder>
                  <Placeholder as={Card.Text} animation="glow">
                    <Placeholder xs={7} /> <Placeholder xs={4} /> <Placeholder xs={4} /> <Placeholder xs={6} /> <Placeholder xs={8} />
                  </Placeholder>
                </>
              )}
            </Card.Body>

            <Card.Body>
              {contract ? (
                <ListGroup variant="flush">
                  <ListGroupItem><strong>{t('manager.createContract.startHour')}:</strong> {contract.startHour}</ListGroupItem>
                  <ListGroupItem><strong>{t('manager.createContract.endHour')}:</strong> {contract.endHour}</ListGroupItem>
                  <ListGroupItem><strong>{t('manager.createContract.totalHoursPerWeek')}:</strong> {contract.totalHoursPerWeek}</ListGroupItem>
                  <ListGroupItem><strong>{t('manager.createContract.salary')}:</strong> ${contract.salary}</ListGroupItem>
                </ListGroup>
              ) : (
                <>
                  <Placeholder as={Card.Title} animation="glow">
                    <Placeholder xs={6} />
                  </Placeholder>
                  <Placeholder as={Card.Text} animation="glow">
                    <Placeholder xs={7} /> <Placeholder xs={4} /> <Placeholder xs={4} /> <Placeholder xs={6} /> <Placeholder xs={8} />
                  </Placeholder>
                </>
              )}
            </Card.Body>

            <Row className="p-3">
              <Col className="mb-3">
                <Card>
                  <Card.Header as="h5">{t('infoCard.supervisor.title')}</Card.Header>
                  <Card.Body>
                    {contract ? (
                      <ListGroup variant="flush">
                        <ListGroupItem><strong>{t('infoCard.supervisor.name')}:</strong> {`${contract.supervisor.firstName} ${contract.supervisor.lastName}`}</ListGroupItem>
                        <ListGroupItem><strong>{t('infoCard.supervisor.position')}:</strong> {contract.supervisor.position}</ListGroupItem>
                        <ListGroupItem><strong>{t('infoCard.supervisor.email')}:</strong> {contract.supervisor.email}</ListGroupItem>
                        <ListGroupItem><strong>{t('infoCard.supervisor.phone')}:</strong> {contract.supervisor.phone}</ListGroupItem>
                      </ListGroup>
                    ) : (
                      <>
                        <Placeholder as={Card.Title} animation="glow">
                          <Placeholder xs={6} />
                        </Placeholder>
                        <Placeholder as={Card.Text} animation="glow">
                          <Placeholder xs={7} /> <Placeholder xs={4} /> <Placeholder xs={4} /> <Placeholder xs={6} /> <Placeholder xs={8} />
                        </Placeholder>
                      </>
                    )}
                  </Card.Body>
                </Card>
              </Col>

              <Col>
                <Card >
                  <Card.Header as="h5">{t('infoCard.student.title')}</Card.Header>
                  <Card.Body>
                    {contract && contract!.application!.cv!.student ? (
                      <ListGroup variant="flush">
                        <ListGroupItem><strong>{t('infoCard.student.name')} :</strong> {`${contract!.application!.cv!.student.firstName} ${contract!.application!.cv!.student.lastName}`}</ListGroupItem>
                        <ListGroupItem><strong>{t('signup.studentId')}:</strong> {contract!.application!.cv!.student.studentNumber}</ListGroupItem>
                        <ListGroupItem><strong>{t('infoCard.student.email')}:</strong> {contract!.application!.cv!.student.email}</ListGroupItem>
                        <ListGroupItem><strong>{t('infoCard.student.phone')}:</strong> {contract!.application!.cv!.student.phone}</ListGroupItem>
                      </ListGroup>
                    ) : (
                      <>
                        <Placeholder as={Card.Title} animation="glow">
                          <Placeholder xs={6} />
                        </Placeholder>
                        <Placeholder as={Card.Text} animation="glow">
                          <Placeholder xs={7} /> <Placeholder xs={4} /> <Placeholder xs={4} /> <Placeholder xs={6} /> <Placeholder xs={8} />
                        </Placeholder>
                      </>
                    )}
                  </Card.Body>
                </Card>
              </Col>
            </Row>
          </Card>

          <Row className="mb-3">
            {getMissingSignatures().map((signatoryType: string) => (
              <Col key={signatoryType} className="mb-3">
                {signatoryType.toUpperCase() === userType ? (
                  <Badge className="p-2"
                    onClick={didEmployerAndStudentSign() ? handleSign : undefined}
                    role={didEmployerAndStudentSign() ? 'button' : undefined}
                    bg={didEmployerAndStudentSign() ? 'primary' : 'secondary'}
                  >
                    {didEmployerAndStudentSign() ? t('signature.sign') : t('signature.waiting')}
                  </Badge>
                ) :
                  <Badge bg="danger" className="p-2">
                    {getSignatoryType(signatoryType)}
                  </Badge>
                }
              </Col>
            ))}
            {signatures.map((signature: Signature) => (
              <Col key={signature.id} className="mb-3">
                <Badge bg="success" className="p-2">
                  {`${signature.signatoryName} ${t('signature.signedOn')} ${new Date(signature?.signatureDate).toLocaleDateString()}`}
                </Badge>
              </Col>
            ))}
          </Row>
        </Col>
      </Row >
    </Container >
  );
};

export default SignContract;
