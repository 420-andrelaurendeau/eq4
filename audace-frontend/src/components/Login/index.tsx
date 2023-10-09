import { useState } from "react";
import { Button, Form } from "react-bootstrap";
import FormInput from "../FormInput";
import { LoginRequest } from "../../model/auth";
import { authenticate, getUserId, login, logout } from "../../services/authService";
import { useNavigate } from "react-router-dom";
import { useTranslation } from "react-i18next";
import { getUserById } from "../../services/userService";
import { User } from "../../model/user";

const LoginForm = () => {
  const { t } = useTranslation();
  const [identification, setIdentification] = useState<string>("");
  const [password, setPassword] = useState<string>("");
  const [errors, setErrors] = useState<string[]>([]);
  const [areCredentialsValid, setAreCredentialsValid] = useState<boolean>(true);
  const [isDisabled, setIsDisabled] = useState<boolean>(false);
  const navigate = useNavigate();

  const submitForm = () => {
    if (!validateForm()) return;

    setIsDisabled(true);

    let loginRequest: LoginRequest = {
      identification: identification,
      password: password,
    };

    login(loginRequest)
      .then((response) => {
        authenticate(response.data);

        const id = getUserId();

        if (id == null) {
          logout();
          navigate("/pageNotFound");
          return;
        }

        getUserById(parseInt(id))
          .then((res) => {
            let user: User = res.data;
            if (user.type === "student") navigate("/student");
            else if (user.type === "manager") navigate("/manager");
            else if (user.type === "employer") navigate("/employer");
            else navigate("/pageNotFound");
          })
          .catch((err) => {
            console.log(err);
            logout();
            navigate("/pageNotFound");
          });

        navigate("/");
      })
      .catch((error) => {
        if (error.response.status === 401 || error.response.status === 403)
          setAreCredentialsValid(false);

        setIsDisabled(false);
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
          formError={"login.errors.emptyIdentification"}
        />
        <FormInput
          label="login.password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          controlId="formBasicPassword"
          errors={errors}
          formError={"login.errors.emptyPassword"}
          type="password"
        />
        <Button onClick={submitForm} disabled={isDisabled}>
          {t("signin")}
        </Button>
        {!areCredentialsValid && (
          <p className="invalid-credentials">
            {t("login.errors.invalidCredentials")}
          </p>
        )}
      </Form>
    </>
  );
};

export default LoginForm;
