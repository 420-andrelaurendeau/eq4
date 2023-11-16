import { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { Card, ListGroup, ListGroupItem, Container, Row, Col, Button, Placeholder } from 'react-bootstrap';
import { Contract } from '../../model/contract';
import { getContractByIdAsManager, getContractByIdAsStudent } from '../../services/contractService';
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

  }, [UserType, id]);

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
              {contract ? (
                <ListGroup variant="flush">
                  <ListGroupItem><strong>{t('manager.createContract.startHour')}:</strong> {contract.startHour}</ListGroupItem>
                  <ListGroupItem><strong>{t('manager.createContract.endHour')}:</strong> {contract.endHour}</ListGroupItem>
                  <ListGroupItem><strong>{t('manager.createContract.totalHoursPerWeek')}:</strong> {contract.totalHoursPerWeek}</ListGroupItem>
                  <ListGroupItem><strong>{t('manager.createContract.salary')}:</strong> {contract.salary}</ListGroupItem>
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

          <Card className="mb-3">
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

          <Card className="mb-3">
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

          <Card className="mb-3">
            <Card.Header as="h5">{t('infoCard.employer.title')}</Card.Header>
            <Card.Body>
              {contract && contract!.application!.offer!.employer ? (
                <ListGroup variant="flush">
                  <ListGroupItem><strong>{t('infoCard.employer.name')} :</strong> {`${contract!.application!.offer!.employer.firstName} ${contract!.application!.offer!.employer.lastName}`}</ListGroupItem>
                  <ListGroupItem><strong>{t('signup.companyNameEntry')}:</strong> {contract!.application!.offer!.employer.organisation}</ListGroupItem>
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

          <Card className="mb-3">
            <Card.Header as="h5">{t('offersList.title')}</Card.Header>
            <Card.Body>
              {contract && contract!.application!.offer ? (
                <ListGroup variant="flush">
                  <ListGroupItem><strong>{t('addOffer.title')}:</strong> {contract!.application!.offer.title}</ListGroupItem>
                  <ListGroupItem><strong>{t('addOffer.description')}:</strong> {contract!.application!.offer.description}</ListGroupItem>
                  <ListGroupItem><strong>{t('addOffer.startDate')}:</strong> {contract!.application!.offer.internshipStartDate.toString()}</ListGroupItem>
                  <ListGroupItem><strong>{t('addOffer.endDate')}:</strong> {contract!.application!.offer.internshipEndDate.toString()}</ListGroupItem>
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
