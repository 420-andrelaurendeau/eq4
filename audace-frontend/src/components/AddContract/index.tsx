import React, { useState } from 'react';
import http from "../../constants/http";
import { useNavigate } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import FormInput from '../FormInput';
import { Form, Button, Container, Row, Col, Alert } from 'react-bootstrap';
import { managerCreateContract } from '../../services/contractService';
import { Contract } from '../../model/contract';
import { Employer } from '../../model/user';
import Application from '../../model/application';

const AddContract = () => {
  const navigate = useNavigate();
  const { t } = useTranslation();
  const [errors, setErrors] = useState<string[]>([]);
  const [isLoading, setIsLoading] = useState(false);

  const [officeName, setOfficeName] = useState('');
  const [startHour, setStartHour] = useState(9);
  const [endHour, setEndHour] = useState(5);
  const [totalHoursPerWeek, setTotalHoursPerWeek] = useState(40);
  const [salary, setSalary] = useState(15.25);
  const [internTasksAndResponsibilities, setInternTasksAndResponsibilities] = useState('');
  const [employer, setEmployer] = useState<Employer>();
  const [application, setApplication] = useState<Application>();

  const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    setIsLoading(true);
    try {
      if (!validateForm()) {
        console.log("Form is not valid");
        setIsLoading(false);
        return;
      }
    } catch (error) {
      console.error(error);
      setIsLoading(false);
    }
  };

  const addContract = async (contractData: Contract) => {
    try {
      managerCreateContract(contractData);

    } catch (error) {
      console.error("There was an error sending the data:", error);
    }
  };

  const validateForm = (): boolean => {
    if (officeName === '') errors.push("manager.createContract.errors.emptyOfficeName");
    if (startHour <= 0 || startHour >= 24) errors.push("manager.createContract.errors.invalidStartHour");
    if (endHour <= 0 || endHour >= 24) errors.push("manager.createContract.errors.invalidEndHour");
    if (totalHoursPerWeek <= 0 || totalHoursPerWeek > 168) errors.push("manager.createContract.errors.invalidTotalHoursPerWeek");
    if (salary <= 0) errors.push("manager.createContract.errors.invalidSalary");
    if (internTasksAndResponsibilities === '') errors.push("manager.createContract.errors.emptyInternTasksAndResponsibilities");
    setErrors(errors);
    return errors.length === 0;
  }

  return (
    <Container>
      <h1>{t('manager.createContract.title')}</h1>
      {errors.length > 0 && (
        <Alert variant="danger" onClose={() => setErrors([])} dismissible>
          {errors.map((error, index) => (
            <p key={index}>{t(error)}</p>
          ))}
        </Alert>
      )}
      <Form onSubmit={handleSubmit}>
        <FormInput
          label="manager.createContract.officeName"
          value={officeName}
          onChange={(e) => setOfficeName(e.target.value)}
          controlId="formBasicOfficeName"
          errors={errors}
          formError={"contract.errors.emptyOfficeName"}
        />
        <Row className="mb-3">
          <Col>
            <Form.Group controlId="formBasicStartHour">
              <Form.Label>{t("manager.createContract.startHour")}</Form.Label>
              <Form.Control
                type="number"
                size="sm"
                min="1"
                value={startHour}
                isInvalid={errors.includes("contract.errors.invalidStartHour")}
                onChange={(e) => setStartHour(Number(e.target.value))}
              />
              {errors.includes("contract.errors.invalidStartHour")
              }
            </Form.Group>
          </Col>
          <Col>
            <Form.Group controlId="formBasicEndHour">
              <Form.Label>{t("manager.createContract.endHour")}</Form.Label>
              <Form.Control
                type="number"
                size="sm"
                min="1"
                value={endHour}
                isInvalid={errors.includes("contract.errors.invalidEndHour")}
                onChange={(e) => setEndHour(Number(e.target.value))}
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
        <Form.Group controlId="formBasicInternTasksAndResponsibilities">
          <Form.Label>{t("manager.createContract.internTasksAndResponsibilities")}</Form.Label>
          <Form.Control
            as="textarea"
            value={internTasksAndResponsibilities}
            onChange={(e) => setInternTasksAndResponsibilities(e.target.value)}
          />
          {errors.includes("contract.errors.emptyInternTasksAndResponsibilities")}
        </Form.Group>
        <Button variant="primary" type="submit" disabled={isLoading} className="mt-3">
          {isLoading ? t('common.loading') : t('common.submit')}
        </Button>
      </Form>
    </Container>
  );
};

export default AddContract;
