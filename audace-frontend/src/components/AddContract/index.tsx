import React, { useEffect, useState } from 'react';
import { useParams } from "react-router";
import { useNavigate } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import { Form, Button, Container, Row, Col, Alert } from 'react-bootstrap';
import { Contract } from '../../model/contract';
import Application from '../../model/application';
import { createContract, getApplicationById } from '../../services/managerService';
import { getContractByApplicationId } from '../../services/applicationService';
import { Employer, Student } from '../../model/user';
import InfoCardView from '../../views/InfoCardView';

const AddContract = () => {
  const navigate = useNavigate();
  const { applicationId } = useParams();
  const { t } = useTranslation();
  const [errors, setErrors] = useState<string[]>([]);
  const [isLoading, setIsLoading] = useState(false);
  const [application, setApplication] = useState<Application>();
  const [startHour, setStartHour] = useState('09:00');
  const [endHour, setEndHour] = useState('17:00');
  const [totalHoursPerWeek, setTotalHoursPerWeek] = useState(40);
  const [salary, setSalary] = useState(15.25);
  const [isContractCreated, setIsContractCreated] = useState(true);

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

    getContractByApplicationId(application.id!)
      .then((res) => {
        if (res.data !== null) {
          navigate('/manager');
        }
      })
      .catch((error) => {
        console.error("Error fetching contract: " + error);
        setIsContractCreated(false);
      });
  })

  const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    if (!validateForm()) return;
    setIsLoading(true);

    const formData: Contract = {
      startHour: startHour,
      endHour: endHour,
      totalHoursPerWeek: totalHoursPerWeek,
      salary: salary,
      supervisor: application!.offer!.employer,
      application: application!
    };

    try {
      await createContract(formData);
      navigate('/manager/createdContract');
    } catch (error) {
      console.error(error);
      setIsLoading(false);
    }
  };

  const validateForm = (): boolean => {
    const errorsToDisplay: string[] = [];

    if (application === undefined) errorsToDisplay.push("manager.createContract.errors.applicationNotFound");
    if (totalHoursPerWeek <= 0 || totalHoursPerWeek > 168) errorsToDisplay.push("manager.createContract.errors.invalidTotalHoursPerWeek");
    if (salary <= 0) errorsToDisplay.push("manager.createContract.errors.invalidSalary");

    setErrors(errorsToDisplay);
    return errorsToDisplay.length === 0;
  }

  return (
    <Container>
      <h1>{t('manager.createContract.title')}</h1>

      <Row xs={1} md={2} className="g-4 mb-3">
        <Col>
          <InfoCardView employer={application?.offer?.employer as Employer} />
        </Col>
        <Col>
          <InfoCardView student={application?.cv?.student as Student} />
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
        <Row className="mb-3">
          <Col>
            <Form.Group controlId="formBasicStartHour">
              <Form.Label>{t("manager.createContract.startHour")}</Form.Label>
              <Form.Control
                type="time"
                size="sm"
                min="1"
                step={2700}
                value={startHour}
                isInvalid={errors.includes("contract.errors.invalidStartHour")}
                onChange={(e) => setStartHour(e.target.value)}
              />
              {errors.includes("contract.errors.invalidStartHour")
              }
            </Form.Group>
          </Col>
          <Col>
            <Form.Group controlId="formBasicEndHour">
              <Form.Label>{t("manager.createContract.endHour")}</Form.Label>
              <Form.Control
                type="time"
                size="sm"
                min="1"
                value={endHour}
                isInvalid={errors.includes("contract.errors.invalidEndHour")}
                onChange={(e) => setEndHour(e.target.value)}
              />
              {errors.includes("contract.errors.invalidEndHour")
              }
            </Form.Group>
          </Col>
        </Row>
        <Row className="mb-3">
          <Col>
            <Form.Group controlId="formBasicTotalHoursPerWeek">
              <Form.Label>{t("manager.createContract.totalHoursPerWeek")}</Form.Label>
              <Form.Control
                type="number"
                size="sm"
                min="1"
                value={totalHoursPerWeek}
                isInvalid={errors.includes("contract.errors.invalidTotalHoursPerWeek")}
                onChange={(e) => setTotalHoursPerWeek(Number(e.target.value))}
              />
              {errors.includes("contract.errors.invalidTotalHoursPerWeek")
              }
            </Form.Group>
          </Col>
          <Col>
            <Form.Group controlId="formBasicSalary">
              <Form.Label>{t("manager.createContract.salary")}</Form.Label>
              <Form.Control
                type="text"
                size="sm"
                min="1"
                value={salary}
                isInvalid={errors.includes("contract.errors.invalidSalary")}
                onChange={(e) => setSalary(Number(e.target.value))}
                pattern="[0-9]+([,.][0-9]+)?"
              />
              {errors.includes("contract.errors.invalidSalary")
              }
            </Form.Group>
          </Col>
        </Row>
        <Button variant="primary" type="submit" disabled={isLoading} className="mt-3">
          {isLoading ? t('common.loading') : t('common.submit')}
        </Button>
      </Form>
    </Container>
  );
};

export default AddContract;
