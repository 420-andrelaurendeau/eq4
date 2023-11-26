import React, { useEffect, useState } from "react";
import { useParams } from "react-router";
import { useNavigate } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import { Form, Button, Container, Row, Col, Alert, Card, Accordion } from 'react-bootstrap';
import { Contract } from '../../model/contract';
import Application from '../../model/application';
import { getApplicationById } from '../../services/managerService';
import { Employer, Student } from '../../model/user';
import InfoCard from '../InfoCard';
import { createContract, getContractByApplicationId } from '../../services/contractService';
import { Authority } from '../../model/auth';

const AddContract = () => {
  const navigate = useNavigate();
  const { applicationId } = useParams();
  const { t } = useTranslation();
  const [errors, setErrors] = useState<string[]>([]);
  const [isLoading, setIsLoading] = useState(false);
  const [application, setApplication] = useState<Application>();
  const [startHour, setStartHour] = useState("09:00");
  const [endHour, setEndHour] = useState("17:00");
  const [totalHoursPerWeek, setTotalHoursPerWeek] = useState(40);
  const [salary, setSalary] = useState(15.25);
  const [isContractCreated, setIsContractCreated] = useState(true);

  const [supervisorFirstName, setSupervisorFirstName] = useState("");
  const [supervisorLastName, setSupervisorLastName] = useState("");
  const [supervisorEmail, setSupervisorEmail] = useState("");
  const [supervisorPhone, setSupervisorPhone] = useState("");
  const [supervisorPosition, setSupervisorPosition] = useState("");
  const [supervisorExtension, setSupervisorExtension] = useState("");

  useEffect(() => {
    getApplicationById(parseInt(applicationId!))
      .then((applicationResponse) => {
        setApplication(applicationResponse.data);
      })
      .catch((error) => {
        console.error("Error fetching application: " + error);
      });
  }, [applicationId]);

  useEffect(() => {
    if (!isContractCreated) return;
    if (application === undefined) return;

    getContractByApplicationId(application.id!, Authority.MANAGER)
      .then((res) => {
        if (res.data !== null) {
          navigate("/manager");
        }
      })
      .catch((error) => {
        console.error("Error fetching contract: " + error);
        setIsContractCreated(false);
      });
  });

  const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    if (!validateForm()) return;
    setIsLoading(true);

    const formData: Contract = {
      startHour: startHour,
      endHour: endHour,
      totalHoursPerWeek: totalHoursPerWeek,
      salary: salary,
      supervisor: {
        firstName: supervisorFirstName,
        lastName: supervisorLastName,
        position: supervisorPosition,
        email: supervisorEmail,
        phone: supervisorPhone,
        extension: supervisorExtension,
      },
      application: application!,
    };

    try {
      await createContract(formData);
      navigate("/manager/createdContract");
    } catch (error) {
      console.error(error);
      setIsLoading(false);
    }
  };

  const validateForm = (): boolean => {
    const errorsToDisplay: string[] = [];

    if (application === undefined)
      errorsToDisplay.push("manager.createContract.errors.applicationNotFound");
    if (totalHoursPerWeek <= 0 || totalHoursPerWeek > 168)
      errorsToDisplay.push(
        "manager.createContract.errors.invalidTotalHoursPerWeek"
      );
    if (salary <= 0)
      errorsToDisplay.push("manager.createContract.errors.invalidSalary");

    setErrors(errorsToDisplay);
    return errorsToDisplay.length === 0;
  };

  return (
    <Container className="p-3">
      <h1>{t("manager.createContract.title")}</h1>

      <Row xs={1} md={2} className="g-4 mb-3">
        <Col>
          <CompanyInfoCard
            employer={application?.offer?.employer as Employer}
          />
        </Col>
        <Col>
          <StudentInfoCard student={application?.cv?.student as Student} />
        </Col>
      </Row>

      {errors.length > 0 && (
        <Alert variant="danger" onClose={() => setErrors([])} dismissible>
          {errors.map((error, index) => (
            <p key={index}>{t(error)}</p>
          ))}
        </Alert>
      )}
      <Form onSubmit={handleSubmit}>
        <Accordion
          defaultActiveKey={["0"]}
          alwaysOpen
          className="shadow-sm mb-4"
        >
          <Accordion.Item eventKey="0">
            <Accordion.Header>
              <Card.Title>Supervisor</Card.Title>
            </Accordion.Header>
            <Accordion.Body>
              <Card.Body>
                <Row className="mb-3">
                  <Col>
                    <Form.Group controlId="formBasicSupervisorFirstName">
                      <Form.Label>{t("manager.createContract.supervisor.firstName")}</Form.Label>
                      <Form.Control type="text" size="sm" value={supervisorFirstName}
                        onChange={(e) => setSupervisorFirstName(e.target.value)}
                      />
                      {errors.includes("contract.errors.invalidStartHour")}
                    </Form.Group>
                  </Col>
                  <Col>
                    <Form.Group controlId="formBasicSupervisorLastName">
                      <Form.Label>{t("manager.createContract.supervisor.lastName")}</Form.Label>
                      <Form.Control type="text" size="sm" value={supervisorLastName}
                        onChange={(e) => setSupervisorLastName(e.target.value)}
                      />
                      {errors.includes("contract.errors.invalidEndHour")}
                    </Form.Group>
                  </Col>
                </Row>
                <Row className="mb-3">
                  <Col>
                    <Form.Group controlId="formBasicSupervisorEmail">
                      <Form.Label>{t("manager.createContract.supervisor.email")}</Form.Label>
                      <Form.Control type="email" size="sm" value={supervisorEmail}
                        onChange={(e) => setSupervisorEmail(e.target.value)}
                      />
                      {errors.includes("contract.errors.invalidStartHour")}
                    </Form.Group>
                  </Col>
                  <Col>
                    <Form.Group controlId="formBasicSupervisorPosition">
                      <Form.Label>{t("manager.createContract.supervisor.position")}</Form.Label>
                      <Form.Control type="text" size="sm" value={supervisorPosition}
                        onChange={(e) => setSupervisorPosition(e.target.value)}
                      />
                      {errors.includes("contract.errors.invalidEndHour")}
                    </Form.Group>
                  </Col>
                </Row>
                <Row className="mb-3">
                  <Col>
                    <Form.Group controlId="formBasicSupervisorPhone">
                      <Form.Label>{t("manager.createContract.supervisor.phone")}</Form.Label>
                      <Form.Control type="phone" size="sm" value={supervisorPhone}
                        onChange={(e) => setSupervisorPhone(e.target.value)}
                      />
                      {errors.includes("contract.errors.invalidStartHour")}
                    </Form.Group>
                  </Col>
                  <Col>
                    <Form.Group controlId="formBasicSupervisorExtension">
                      <Form.Label>{t("manager.createContract.supervisor.extension")}</Form.Label>
                      <Form.Control type="text" size="sm" value={supervisorExtension}
                        onChange={(e) => setSupervisorExtension(e.target.value)}
                      />
                      {errors.includes("contract.errors.invalidEndHour")}
                    </Form.Group>
                  </Col>
                </Row>
              </Card.Body>
            </Accordion.Body>
          </Accordion.Item>
        </Accordion>

        <Row className="mb-3">
          <Col>
            <Form.Group controlId="formBasicStartHour">
              <Form.Label>{t("manager.createContract.startHour")}</Form.Label>
              <Form.Control type="time" size="sm" min="1" step={2700} value={startHour}
                isInvalid={errors.includes("contract.errors.invalidStartHour")}
                onChange={(e) => setStartHour(e.target.value)}
              />
              {errors.includes("contract.errors.invalidStartHour")}
            </Form.Group>
          </Col>
          <Col>
            <Form.Group controlId="formBasicEndHour">
              <Form.Label>{t("manager.createContract.endHour")}</Form.Label>
              <Form.Control type="time" size="sm" min="1" value={endHour}
                isInvalid={errors.includes("contract.errors.invalidEndHour")}
                onChange={(e) => setEndHour(e.target.value)}
              />
              {errors.includes("contract.errors.invalidEndHour")}
            </Form.Group>
          </Col>
        </Row>
        <Row className="mb-3">
          <Col>
            <Form.Group controlId="formBasicTotalHoursPerWeek">
              <Form.Label>{t("manager.createContract.totalHoursPerWeek")}</Form.Label>
              <Form.Control type="number" size="sm" min="1" value={totalHoursPerWeek}
                isInvalid={errors.includes("contract.errors.invalidTotalHoursPerWeek")}
                onChange={(e) => setTotalHoursPerWeek(Number(e.target.value))}
              />
              {errors.includes("contract.errors.invalidTotalHoursPerWeek")}
            </Form.Group>
          </Col>
          <Col>
            <Form.Group controlId="formBasicSalary">
              <Form.Label>{t("manager.createContract.salary")}</Form.Label>
              <Form.Control type="text" size="sm" min="1" value={salary}
                isInvalid={errors.includes("contract.errors.invalidSalary")}
                onChange={(e) => setSalary(Number(e.target.value))}
                pattern="[0-9]+([,.][0-9]+)?"
              />
              {errors.includes("contract.errors.invalidSalary")}
            </Form.Group>
          </Col>
        </Row>
        <Button
          variant="primary"
          type="submit"
          disabled={isLoading}
          className="mt-3"
        >
          {isLoading ? t("common.loading") : t("common.submit")}
        </Button>
      </Form>
    </Container>
  );
};

export default AddContract;
