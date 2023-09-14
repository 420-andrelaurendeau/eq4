import { useState } from "react";
import { Button, Form, Row, Col } from "react-bootstrap";
import { employerSignup } from "../../../services/signupService";
import { Employer } from "../../../model/user";
import { useTranslation } from "react-i18next";
import {
  validateEmail,
  validatePassword,
} from "../../../services/validationService";

const EmployerSignup = () => {
  const { t } = useTranslation();
  const [organisation, setOrganisation] = useState<string>("");
  const [position, setPosition] = useState<string>("");
  const [firstName, setFirstName] = useState<string>("");
  const [lastName, setLastName] = useState<string>("");
  const [email, setEmail] = useState<string>("");
  const [phone, setPhone] = useState<string>("");
  const [extension, setExtension] = useState<string>("");
  const [address, setAddress] = useState<string>("");
  const [city, setCity] = useState<string>("");
  const [postalCode, setPostalCode] = useState<string>("");
  const [password, setPassword] = useState<string>("");
  const [passwordConfirmation, setPasswordConfirmation] = useState<string>("");

  const handleSubmit = () => {
    if (!validateForm()) return;

    let employer: Employer = {
      organisation: organisation,
      position: position,
      firstName: firstName,
      lastName: lastName,
      email: email,
      phone: phone,
      extension: extension,
      address: `${address}, ${city}, ${postalCode}`,
      password: password,
      offers: []
    };

    employerSignup(employer)
      .then((res) => {})
      .catch((err) => {});
  };

  const validateForm = (): boolean => {
    return (
      validateOrganisation() &&
      validatePosition() &&
      validateFirstName() &&
      validateLastName() &&
      validateEmail(email) &&
      validatePhone() &&
      validateExtension() &&
      validateAddress() &&
      validateCity() &&
      validatePostalCode() &&
      validatePassword(password, passwordConfirmation)
    );
  };

  const validateOrganisation = (): boolean => {
    return organisation !== "";
  };

  const validatePosition = (): boolean => {
    return position !== "";
  };

  const validateFirstName = (): boolean => {
    return firstName !== "";
  };

  const validateLastName = (): boolean => {
    return lastName !== "";
  };

  const validatePhone = (): boolean => {
    return phone !== "" && /^[0-9]{10}$/i.test(phone);
  };

  const validateExtension = (): boolean => {
    return (
      extension !== "" && /^[0-9]+$/i.test(extension) && extension.length <= 5
    );
  };

  const validateAddress = (): boolean => {
    return address !== "";
  };

  const validateCity = (): boolean => {
    return city !== "";
  };

  const validatePostalCode = (): boolean => {
    return postalCode !== "" && /^[A-Za-z0-9\s]+$/i.test(postalCode);
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

        <Row>
          <Form.Group as={Col} controlId="formBasicFirstName">
            <Form.Label>{t("signup.firstNameEntry")}</Form.Label>
            <Form.Control
              type="text"
              value={firstName}
              onChange={(e) => setFirstName(e.target.value)}
            />
          </Form.Group>

          <Form.Group as={Col} controlId="formBasicLastName">
            <Form.Label>{t("signup.lastNameEntry")}</Form.Label>
            <Form.Control
              type="text"
              value={lastName}
              onChange={(e) => setLastName(e.target.value)}
            />
          </Form.Group>
        </Row>

        <Row>
          <Form.Group as={Col} controlId="formBasicPhone">
            <Form.Label>{t("signup.phoneEntry")}</Form.Label>
            <Form.Control
              type="text"
              value={phone}
              onChange={(e) => setPhone(e.target.value)}
              placeholder="e.g. 123-456-7890"
            />
          </Form.Group>

          <Form.Group as={Col} controlId="formBasicExtension">
            <Form.Label>{t("signup.extensionEntry")}</Form.Label>
            <Form.Control
              type="text"
              value={extension}
              onChange={(e) => setExtension(e.target.value)}
              placeholder="e.g. 123"
            />
          </Form.Group>
        </Row>

        <Row>
          <Form.Group as={Col} controlId="formBasicAddress">
            <Form.Label>{t("signup.addressEntry")}</Form.Label>
            <Form.Control
              type="text"
              value={address}
              onChange={(e) => setAddress(e.target.value)}
            />
          </Form.Group>
        </Row>

        <Row>
          <Form.Group as={Col} controlId="formBasicCity">
            <Form.Label>{t("signup.cityEntry")}</Form.Label>
            <Form.Control
              type="text"
              value={city}
              onChange={(e) => setCity(e.target.value)}
            />
          </Form.Group>

          <Form.Group as={Col} controlId="formBasicPostalCode">
            <Form.Label>{t("signup.postalCodeEntry")}</Form.Label>
            <Form.Control
              type="text"
              value={postalCode}
              onChange={(e) => setPostalCode(e.target.value)}
            />
          </Form.Group>
        </Row>

        <Row>
          <Form.Group as={Col} controlId="formBasicEmail">
            <Form.Label>{t("signup.emailEntry")}</Form.Label>
            <Form.Control
              type="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
            />
          </Form.Group>
        </Row>

        <Row>
          <Form.Group as={Col} controlId="formBasicPassword">
            <Form.Label>{t("signup.password")}</Form.Label>
            <Form.Control
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
            />
          </Form.Group>

          <Form.Group as={Col} controlId="formBasicPasswordConfirmation">
            <Form.Label>{t("signup.passwordConfirmation")}</Form.Label>
            <Form.Control
              type="password"
              value={passwordConfirmation}
              onChange={(e) => setPasswordConfirmation(e.target.value)}
            />
          </Form.Group>
        </Row>

        <Button variant="primary" className="mt-3" onClick={handleSubmit}>
          {t("signup.signup")}
        </Button>
      </Form>
    </>
  );
};

export default EmployerSignup;
