import { useState } from "react";
import { Form, Row, Col } from "react-bootstrap";
import { employerSignup } from "../../../services/signupService";
import { Employer, User } from "../../../model/user";
import { useTranslation } from "react-i18next";
import Signup from "..";

const EmployerSignup = () => {
  const { t } = useTranslation();
  const [organisation, setOrganisation] = useState<string>("");
  const [position, setPosition] = useState<string>("");
  const [extension, setExtension] = useState<string>("");

  const handleSubmit = (user: User) => {
    if (!validateForm()) return;

    let employer: Employer = {
      ...user,
      organisation: organisation,
      position: position,
      extension: extension,
      offers: [],
    };

    employerSignup(employer)
      .then((res) => {})
      .catch((err) => {});
  };

  const validateForm = (): boolean => {
    return validateOrganisation() && validatePosition();
  };

  const validateOrganisation = (): boolean => {
    return organisation !== "";
  };

  const validatePosition = (): boolean => {
    return position !== "";
  };

  return (
    <>
      <h3>{t("signup.employerFormTitle")}</h3>
      <Form>
        <Row>
          <Form.Group as={Col} controlId="formBasicCompanyName">
            <Form.Label>{t("signup.companyNameEntry")}</Form.Label>
            <Form.Control
              type="text"
              value={organisation}
              onChange={(e) => setOrganisation(e.target.value)}
            />
          </Form.Group>

          <Form.Group as={Col} controlId="formBasicPosition">
            <Form.Label>{t("signup.positionEntry")}</Form.Label>
            <Form.Control
              type="text"
              value={position}
              onChange={(e) => setPosition(e.target.value)}
              placeholder="e.g. CEO, HR Manager"
            />
          </Form.Group>
        </Row>

        <Signup handleSubmit={handleSubmit} extension={extension} setExtension={setExtension}/>
      </Form>
    </>
  );
};

export default EmployerSignup;
