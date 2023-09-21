import { useState } from "react";
import { Form, Row } from "react-bootstrap";
import { employerSignup } from "../../../services/signupService";
import { Employer, User } from "../../../model/user";
import { useTranslation } from "react-i18next";
import Signup from "..";
import FormInput from "../FormInput";

const EmployerSignup = () => {
  const { t } = useTranslation();
  const [organisation, setOrganisation] = useState<string>("");
  const [position, setPosition] = useState<string>("");
  const [extension, setExtension] = useState<string>("");
  const [errors, setErrors] = useState<string[]>([]);

  const handleSubmit = (user: User) => {
    let employer: Employer = {
      ...user,
      organisation: organisation,
      position: position,
      extension: extension,
      offers: [],
    };

    return employerSignup(employer);
  };

  const validateForm = (errors: string[]): boolean => {
    let isFormValid = true;
    let errorsToDisplay: string[] = [];

    if (!validateOrganisation(errorsToDisplay)) isFormValid = false;
    if (!validatePosition(errorsToDisplay)) isFormValid = false;

    setErrors([...errorsToDisplay, ...errors]);

    return isFormValid;
  };

  const validateOrganisation = (errorsToDisplay: string[]): boolean => {
    if (organisation === "") {
      errorsToDisplay.push("signup.errors.organisation");
      return false;
    }

    return true;
  };

  const validatePosition = (errorsToDisplay: string[]): boolean => {
    if (position === "") {
      errorsToDisplay.push("signup.errors.position");
      return false;
    }

    return true;
  };

  return (
    <>
      <h3>{t("signup.employerFormTitle")}</h3>
      <Form>
        <Row>
          <FormInput 
            label="signup.companyNameEntry"
            value={organisation}
            onChange={(e) => setOrganisation(e.target.value)}
            errors={errors}
            formError="signup.errors.organisation"
            controlId="formBasicCompanyName"
          />

          <FormInput 
            label="signup.positionEntry"
            value={position}
            onChange={(e) => setPosition(e.target.value)}
            errors={errors}
            formError="signup.errors.position"
            controlId="formBasicPosition"
          />
        </Row>

        <Signup
          handleSubmit={handleSubmit}
          extension={extension}
          setExtension={setExtension}
          validateExtraFormValues={validateForm}
          errors={errors}
          setErrors={setErrors}
        />
      </Form>
    </>
  );
};

export default EmployerSignup;
