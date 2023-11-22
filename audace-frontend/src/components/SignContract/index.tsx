import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { Button, Card, Col, Container, ListGroup, ListGroupItem, Placeholder, Row } from 'react-bootstrap';
import { Contract, Signature } from '../../model/contract';
import { getContractById, getSignaturesByContractId, signContract, signContractByManager } from '../../services/contractService';
import { getAuthorities, getUserId } from '../../services/authService';
import { useTranslation } from 'react-i18next';
import { Authority } from '../../model/auth';
import { getUserById } from '../../services/userService';
import { User } from '../../model/user';

const SignContract = () => {
  const { id } = useParams();
  const [contract, setContract] = useState<Contract>();
  const [signatures, setSignatures] = useState<Signature[]>([]);
  const [signatureUsers, setSignatureUsers] = useState<User[]>([]);
  const { t } = useTranslation();
  const userType = getAuthorities()?.[0];

  useEffect(() => {
    const fetchContract = async (contractId: number) => {
      getContractById(contractId, userType!)
        .then((response) => {
          setContract(response.data);
        })
        .catch((error) => {
          console.error("Error fetching contract:", error);
        });
    };

    const fetchSignatures = async (contractId: number) => {
      getSignaturesByContractId(contractId, userType!)
        .then((response) => {
          setSignatures(response.data);
          fetchSignatureUsers();
          console.log("signatures: ", response.data);
        })
        .catch((error) => {
          console.error("Error fetching signatures:", error);
        });
    };

    const fetchSignatureUsers = async () => {
      signatures.forEach((signature) => {
        getUserById(signature.signatoryId)
          .then((response) => {
            console.log("signature user: ", response.data);
            setSignatureUsers([...signatureUsers, response.data]);
          })
          .catch((error) => {
            console.error("Error fetching signatureUsers:", error);
          });
      });
    }

    if (id && userType) {
      const contractId = parseInt(id);
      fetchContract(contractId);
      fetchSignatures(contractId);
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [id, userType]);

  // const handleSign = async (role: string) => {
  //   if (!userId) {
  //     console.error("Invalid user ID");
  //     return;
  //   }

  //   try {
  //     if (contract && role)
  //       if (role === 'manager') {
  //         signContractByManager(userId, contract?.id!);
  //       } else {
  //         signContract(contract?.id!, role);
  //       }

  //     const updatedSignaturesResponse = await getSignaturesByContractId(contract?.id!, role);
  //     setSignatures(updatedSignaturesResponse.data);

  //     const userSignedSignature = updatedSignaturesResponse.data.find(sig => sig?.signatoryId === userId);
  //     if (userSignedSignature) {
  //       setUserSignature(userSignedSignature);
  //     }
  //   } catch (error) {
  //     console.error(`Error signing contract as ${role}:`, error);
  //   }
  // };


  const getSignatureDate = (signature: Signature) => {
    if (signature?.signatoryId === parseInt(getUserId()!)) {
      return new Date(signature?.signatureDate).toLocaleDateString();
    } else {
      return new Date(signature?.signatureDate).toLocaleDateString();
    }
  }

  const isSignedByUser = () => {
    return signatures.find(signature => signature?.signatoryId === parseInt(getUserId()!));
  };

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

          {signatures.map((signature: Signature) => (
            <Card key={signature.id} className="mb-3">
              <Card.Header as="h5">
                {/* {signature.signatoryId === parseInt(getUserId()!) ? t('signature.you') : `${signature.signatory.firstName} ${signature.signatory.lastName}`} */}
              </Card.Header>
              <Card.Body>
                <ListGroup variant="flush">
                  <ListGroupItem><strong>{t('signature.date')}:</strong> {getSignatureDate(signature)}</ListGroupItem>
                </ListGroup>
              </Card.Body>
            </Card>
          ))}

          <Button
            className={isSignedByUser() && userType === Authority.MANAGER ? "signed-button" : ""}
          >
            {isSignedByUser() && userType === Authority.MANAGER ? t('signature.signed') : t('signature.sign')}
          </Button>
        </Col>
      </Row>
    </Container >
  );
};

export default SignContract;
