import React, { useState } from 'react';
import http from "../../constants/http";
import { Form, useNavigate } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import FormInput from '../FormInput';
import { Button } from 'react-bootstrap';
import { managerCreateContract } from '../../services/contractService';
import { Contract } from '../../model/contract';
import { Employer } from '../../model/user';
import Application from '../../model/application';

const AddContract = () => {
  const navigate = useNavigate();
  const { t } = useTranslation();
  const [errors, setErrors] = useState<string[]>([]);
  // const [isLoading, setIsLoading] = useState(false);

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
    // setIsLoading(true);
    try {


    } catch (error) {
      console.error(error);
      // setIsLoading(false);
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
    <>
      <h1>Add Contract</h1>
      <Form className="my-3">
        <FormInput
          label="contract.officeName"
          value={officeName}
          onChange={(e) => setOfficeName(e.target.value)}
          controlId="formBasicOfficeName"
          errors={errors}
          formError={"contract.errors.emptyOfficeName"}
        />
      </Form>
    </>
  );
};

export default AddContract;
