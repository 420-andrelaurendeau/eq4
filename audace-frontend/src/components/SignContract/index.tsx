import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { Card, ListGroup, ListGroupItem, Container, Row, Col, Button } from 'react-bootstrap';
import { Contract } from '../../model/contract';
import { getContractById } from '../../services/contractService';
import { UserType } from "../../model/user";
import { getUserId } from '../../services/authService';
import { getUserById } from '../../services/userService';

const SignContract = () => {
  const { id } = useParams();
  const [contract, setContract] = useState<Contract | null>(null);
  const [UserType, setUserType] = useState<string | null>(null);

  useEffect(() => {

    getUserById(parseInt(getUserId() || '0') || 0).then((res) => setUserType(res.data.type || null)).catch((error) => {
        console.error("Error fetching user:", error);
    });

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
        throw new Error('Function not implemented.');
    }

  return (
    <Container className="mt-4">
      <Row className="justify-content-center">
        <Col md={8}>
          <Card className="mb-3">
            <Card.Header as="h5">Contract Details</Card.Header>
            <Card.Body>
              <ListGroup variant="flush">
                <ListGroupItem><strong>Start Hour:</strong> {startHour}</ListGroupItem>
                <ListGroupItem><strong>End Hour:</strong> {endHour}</ListGroupItem>
                <ListGroupItem><strong>Total Hours Per Week:</strong> {totalHoursPerWeek}</ListGroupItem>
                <ListGroupItem><strong>Salary:</strong> {salary}</ListGroupItem>
              </ListGroup>
            </Card.Body>
          </Card>

          <Card className="mb-3">
            <Card.Header as="h5">Supervisor Details</Card.Header>
            <Card.Body>
              <ListGroup variant="flush">
                <ListGroupItem><strong>Name:</strong> {`${supervisor.firstName} ${supervisor.lastName}`}</ListGroupItem>
                <ListGroupItem><strong>Position:</strong> {supervisor.position}</ListGroupItem>
                <ListGroupItem><strong>Email:</strong> {supervisor.email}</ListGroupItem>
                <ListGroupItem><strong>Phone:</strong> {supervisor.phone}</ListGroupItem>
              </ListGroup>
            </Card.Body>
          </Card>

          {student && (
            <Card className="mb-3">
              <Card.Header as="h5">Student Details</Card.Header>
              <Card.Body>
                <ListGroup variant="flush">
                  <ListGroupItem><strong>Name:</strong> {`${student.firstName} ${student.lastName}`}</ListGroupItem>
                  <ListGroupItem><strong>ID:</strong> {student.studentNumber}</ListGroupItem>
                </ListGroup>
              </Card.Body>
            </Card>
          )}

          {employer && (
            <Card className="mb-3">
              <Card.Header as="h5">Employer Details</Card.Header>
              <Card.Body>
                <ListGroup variant="flush">
                  <ListGroupItem><strong>Name:</strong> {`${employer.firstName} ${employer.lastName}`}</ListGroupItem>
                  <ListGroupItem><strong>Company:</strong> {employer.organisation}</ListGroupItem>
                </ListGroup>
              </Card.Body>
            </Card>
          )}

          {offer && (
            <Card>
              <Card.Header as="h5">Offer Details</Card.Header>
              <Card.Body>
                <ListGroup variant="flush">
                  <ListGroupItem><strong>Title:</strong> {offer.title}</ListGroupItem>
                    <ListGroupItem><strong>Description:</strong> {offer.description}</ListGroupItem>
                  <ListGroupItem><strong>Internship Start Date:</strong> {offer.internshipStartDate.toString()}</ListGroupItem>
                  <ListGroupItem><strong>Internship End Date:</strong> {offer.internshipEndDate.toString()}</ListGroupItem>
                </ListGroup>
                
              </Card.Body>
              
            </Card>
          )}

<Card className="mt-3">
        <Card.Header as="h5">Signature</Card.Header>
        <Card.Body>
          <ListGroup>
            <ListGroupItem className="d-flex justify-content-between align-items-center">
              Manager's Signature
              <div>
                <Button variant="secondary" onClick={() => handleSign('manager')} disabled={UserType !== 'manager'}>
                  Sign
                </Button>
                <span className="ms-2">Signed on: {/* Display manager's sign date */}</span>
              </div>
            </ListGroupItem>
            <ListGroupItem className="d-flex justify-content-between align-items-center">
              Employer's Signature
              <div>
                <Button variant="secondary" onClick={() => handleSign('employer')} disabled={UserType !== 'employer'}>
                  Sign
                </Button>
                <span className="ms-2">Signed on: {/* Display employer's sign date */}</span>
              </div>
            </ListGroupItem>
            <ListGroupItem className="d-flex justify-content-between align-items-center">
              Student's Signature
              <div>
                <Button variant="secondary" onClick={() => handleSign('student')} disabled={UserType !== 'student'}>
                  Sign
                </Button>
                <span className="ms-2">Signed on: {/* Display student's sign date */}</span>
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
