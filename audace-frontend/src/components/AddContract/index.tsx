import React, { useState } from 'react';
import http from "../../constants/http";
import { useNavigate } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import FormInput from '../FormInput';
import { Form, Button, Container, Row } from 'react-bootstrap';
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
  const [startHour, setStartHour] = useState('');
  const [endHour, setEndHour] = useState('');
  const [totalHoursPerWeek, setTotalHoursPerWeek] = useState('');
  const [salary, setSalary] = useState('');
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
        <Row>
          <FormInput
            label="manager.createContract.startHour"
            value={startHour}
            onChange={(e) => setStartHour(e.target.value)}
            controlId="formBasicStartHour"
            errors={errors}
            formError={"contract.errors.emptyStartHour"}
          />
          <FormInput
            label="manager.createContract.endHour"
            value={endHour}
            onChange={(e) => setEndHour(e.target.value)}
            controlId="formBasicEndHour"
            errors={errors}
            formError={"contract.errors.emptyEndHour"}
          />
        </Row>
        <FormInput
          label="manager.createContract.totalHoursPerWeek"
          value={totalHoursPerWeek}
          onChange={(e) => setTotalHoursPerWeek(e.target.value)}
          controlId="formBasicTotalHoursPerWeek"
          errors={errors}
          formError={"contract.errors.emptyTotalHoursPerWeek"}
        />
        <FormInput
          label="manager.createContract.salary"
          value={salary}
          onChange={(e) => setSalary(e.target.value)}
          controlId="formBasicSalary"
          errors={errors}
          formError={"contract.errors.emptySalary"}
        />
        <FormInput
          label="manager.createContract.internTasksAndResponsibilities"
          value={internTasksAndResponsibilities}
          onChange={(e) => setInternTasksAndResponsibilities(e.target.value)}
          controlId="formBasicInternTasksAndResponsibilities"
          errors={errors}
          formError={"contract.errors.emptyInternTasksAndResponsibilities"}
        />
        <Button variant="primary" type="submit" disabled={isLoading}>
          {isLoading ? t('common.loading') : t('common.submit')}
        </Button>
      </Form>
    </Container>
  );
};

export default AddContract;
