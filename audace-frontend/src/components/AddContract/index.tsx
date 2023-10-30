import React, { useState } from 'react';
import http from "../../constants/http";
import { useNavigate } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import FormInput from '../FormInput';
import { Form, Button, Container, Row, Col } from 'react-bootstrap';
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
  const [startHour, setStartHour] = useState(0);
  const [endHour, setEndHour] = useState(0);
  const [totalHoursPerWeek, setTotalHoursPerWeek] = useState(0);
  const [salary, setSalary] = useState(0);
  const [internTasksAndResponsibilities, setInternTasksAndResponsibilities] = useState('');
  const [employer, setEmployer] = useState<Employer>();
  const [application, setApplication] = useState<Application>();

  const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    setIsLoading(true);
    try {

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

  return (
    <Container>
      <h1>{t('manager.createContract.title')}</h1>
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
                isInvalid={errors.includes("contract.errors.emptyStartHour")}
                onChange={(e) => setStartHour(Number(e.target.value))}
              />
              {errors.includes("contract.errors.emptyStartHour")
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
                isInvalid={errors.includes("contract.errors.emptyEndHour")}
                onChange={(e) => setEndHour(Number(e.target.value))}
              />
              {errors.includes("contract.errors.emptyEndHour")
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
                isInvalid={errors.includes("contract.errors.emptyTotalHoursPerWeek")}
                onChange={(e) => setTotalHoursPerWeek(Number(e.target.value))}
              />
              {errors.includes("contract.errors.emptyTotalHoursPerWeek")
              }
            </Form.Group>
          </Col>
          <Col>
            <Form.Group controlId="formBasicSalary">
              <Form.Label>{t("manager.createContract.salary")}</Form.Label>
              <Form.Control
                type="number"
                size="sm"
                min="1"
                value={salary}
                isInvalid={errors.includes("contract.errors.emptySalary")}
                onChange={(e) => setSalary(Number(e.target.value))}
              />
              {errors.includes("contract.errors.emptySalary")
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
