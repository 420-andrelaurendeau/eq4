import { useState } from "react";
import { Button, Form } from "react-bootstrap";
import FormInput from "../FormInput";
import { LoginRequest } from "../../model/auth";
import { authenticate, login } from "../../services/loginService";

const LoginForm = () => {
  const [identification, setIdentification] = useState<string>("");
  const [password, setPassword] = useState<string>("");
  const [errors, setErrors] = useState<string[]>([]);

  const submitForm = () => {
    if (!validateForm()) return;

    let loginRequest: LoginRequest = {
      identification: identification,
      password: password,
    };

    login(loginRequest)
      .then((response) => {
        authenticate(response.data);
      })
      .catch((error) => {
        console.log(error);
      });
  };

  const validateForm = (): boolean => {
    let isFormValid = true;
    let errorsToDisplay: string[] = [];

    if (!validateIdentification(errorsToDisplay)) isFormValid = false;
    if (!validatePassword(errorsToDisplay)) isFormValid = false;

    setErrors(errorsToDisplay);

    return isFormValid;
  };

  const validateIdentification = (errorsToDisplay: string[]): boolean => {
    if (identification === "") {
      errorsToDisplay.push("login.errors.emptyIdentification");
      return false;
    }

    return true;
  };

  const validatePassword = (errorsToDisplay: string[]): boolean => {
    if (password === "") {
      errorsToDisplay.push("login.errors.emptyPassword");
      return false;
    }

    return true;
  };

  return (
    <>
      <Form>
        <FormInput
          label="login.identification"
          value={identification}
          onChange={(e) => setIdentification(e.target.value)}
          controlId="formBasicIdentification"
          errors={errors}
          formError={""}
        />
        <FormInput
          label="login.password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          controlId="formBasicPassword"
          errors={errors}
          formError={""}
          type="password"
        />
        <Button onClick={submitForm}>Submit</Button>
      </Form>
    </>
  );
};

export default LoginForm;
