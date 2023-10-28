import React, { useState } from 'react';
import http from "../../constants/http";
import { Form, useNavigate } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import FormInput from '../FormInput';
import { Button } from 'react-bootstrap';

const AddContract = () => {
  const navigate = useNavigate();
  const { t } = useTranslation();
  const [officeName, setOfficeName] = useState('');
  const [startHour, setStartHour] = useState('');
  const [endHour, setEndHour] = useState('');
  const [totalHoursPerWeek, setTotalHoursPerWeek] = useState('');
  const [salary, setSalary] = useState('');
  const [internTasksAndResponsibilities, setInternTasksAndResponsibilities] = useState('');
  const [errors, setErrors] = useState<string[]>([]);
  const [isLoading, setIsLoading] = useState(false);

  const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    setIsLoading(true);
    try {
      await addContract({ name, description });

    } catch (error) {
      console.error(error);
      setIsLoading(false);
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
