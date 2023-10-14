import { useCallback, useEffect, useState } from "react";
import { Alert, Button, Form } from "react-bootstrap";
import FormInput from "../FormInput";
import { LoginRequest } from "../../model/auth";
import {
  authenticate,
  getUserId,
  hasSessionExpiredRecently,
  isConnected,
  login,
  logout,
} from "../../services/authService";
import { useLocation, useNavigate } from "react-router-dom";
import { useTranslation } from "react-i18next";
import { getUserById } from "../../services/userService";

const LoginForm = () => {
  const { t } = useTranslation();
  const [identification, setIdentification] = useState<string>("");
  const [password, setPassword] = useState<string>("");
  const [errors, setErrors] = useState<string[]>([]);
  const [areCredentialsValid, setAreCredentialsValid] = useState<boolean>(true);
  const [isDisabled, setIsDisabled] = useState<boolean>(false);
  const navigate = useNavigate();
  const location = useLocation();
  const [showExpiredSessionNotif, setShowExpiredSessionNotif] = useState<boolean>(false);

  const isSessionProperlyExpired = useCallback(() => {
    return (
        location.pathname === "/login/disconnected" &&
        !isConnected() &&
        hasSessionExpiredRecently()
    );
  }, [location.pathname]);

  useEffect(() => {
    if (!isSessionProperlyExpired()) return;

    setShowExpiredSessionNotif(true);
  }, [isSessionProperlyExpired]);

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
                navigateToUserTypeHomePage(res.data.type!);
              })
              .catch((err) => {
                console.log(err);
                logout();
                navigate("/pageNotFound");
              });
        })
        .catch((error) => {
          if (error.response.status === 401 || error.response.status === 403)
            setAreCredentialsValid(false);

          setIsDisabled(false);
        });
  };

  const navigateToUserTypeHomePage = (userType: string) => {
    switch (userType) {
      case "student":
        navigate("/student");
        break;
      case "manager":
        navigate("/manager");
        break;
      case "employer":
        navigate("/employer");
        break;
      default:
        navigate("/pageNotFound");
        break;
    }
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
        <Form className="my-3">
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
        {showExpiredSessionNotif && (
            <Alert variant="danger">{t("login.errors.sessionExpired")}</Alert>
        )}
      </>
  );
};

export default LoginForm;