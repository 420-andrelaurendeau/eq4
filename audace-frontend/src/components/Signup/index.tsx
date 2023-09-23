import { useState } from "react";
import { Button, Row } from "react-bootstrap";
import { useTranslation } from "react-i18next";
import { User } from "../../model/user";
import { validateEmail, validatePassword } from "../../services/validationService";
import { AxiosResponse } from "axios";
import { useNavigate } from "react-router-dom";
import FormInput from "./FormInput";
import './styles.css'

interface Props {
    handleSubmit: (user: User) => Promise<AxiosResponse>;
    extension?: string;
    setExtension?: (extension: string) => void;
    errors: string[];
    setErrors: (errors: string[]) => void;
    validateExtraFormValues?: (errorsToDisplay: string[]) => boolean;
}

const Signup = ({handleSubmit, extension, setExtension, errors, setErrors, validateExtraFormValues}: Props) => {
    const {t} = useTranslation();
    const navigate = useNavigate();
    const [firstName, setFirstName] = useState<string>("");
    const [lastName, setLastName] = useState<string>("");
    const [address, setAddress] = useState<string>("");
    const [city, setCity] = useState<string>("");
    const [postalCode, setPostalCode] = useState<string>("");
    const [phone, setPhone] = useState<string>("");
    const [email, setEmail] = useState<string>("");
    const [password, setPassword] = useState<string>("");
    const [passwordConfirmation, setPasswordConfirmation] = useState<string>("");
    const [isDisabled, setIsDisabled] = useState<boolean>(false);
    const [unexpectedError, setUnexpectedError] = useState<string>("");

    const submitForm = () => {
        if (!validateForm()) return;

        // TODO: add hashing for password

        let user: User = {
            firstName: firstName,
            lastName: lastName,
            address: `${address}, ${city}, ${postalCode}`,
            phone: phone,
            email: email,
            password: password
        };

        sendRequest(user);
    };

    const sendRequest = (user: User) => {
        setIsDisabled(true);

        handleSubmit(user)
            .then((_) => {
                setUnexpectedError("");
                navigate("/");
            })
            .catch((err) => {
                setIsDisabled(false);
                setUnexpectedError(err.status);
            });
    };

    const validateForm = (): boolean => {
        let isFormValid = true;
        let errorsToDisplay: string[] = [];

        if (!validateFirstName(errorsToDisplay)) isFormValid = false;
        if (!validateLastName(errorsToDisplay)) isFormValid = false;
        if (!validateAddress(errorsToDisplay)) isFormValid = false;
        if (!validateCity(errorsToDisplay)) isFormValid = false;
        if (!validatePostalCode(errorsToDisplay)) isFormValid = false;
        if (!validatePhone(errorsToDisplay)) isFormValid = false;

        if (!validateEmail(email)) {
            errorsToDisplay.push("signup.errors.email");
            isFormValid = false;
        }
        if (!validatePassword(password, passwordConfirmation)) {
            errorsToDisplay.push("signup.errors.password");
            isFormValid = false;
        }

        setErrors(errorsToDisplay);
        if (validateExtraFormValues !== undefined) isFormValid = validateExtraFormValues(errorsToDisplay);

        return isFormValid;
    };

    const validateFirstName = (errorsToDisplay: string[]): boolean => {
        if (firstName === "") {
            errorsToDisplay.push("signup.errors.firstName");
            return false;
        }

        return true;
      };
    
      const validateLastName = (errorsToDisplay: string[]): boolean => {
        if (lastName === "") {
            errorsToDisplay.push("signup.errors.lastName");
            return false;
        }
    
        return true;
      };
    
      const validatePhone = (errorsToDisplay: string[]): boolean => {
        if (phone === "" || !/^[0-9]{10}$/i.test(phone)) {
            errorsToDisplay.push("signup.errors.phone");
            return false;
        }
    
        return true;
      };
    
      const validateAddress = (errorsToDisplay: string[]): boolean => {
        if (address === "") {
            errorsToDisplay.push("signup.errors.address");
            return false;
        }
    
        return true;
      };
    
      const validateCity = (errorsToDisplay: string[]): boolean => {
        if (city === "") {
            errorsToDisplay.push("signup.errors.city");
            return false;
        }
    
        return true;
      };
    
      const validatePostalCode = (errorsToDisplay: string[]): boolean => {
        if (postalCode === "" || !/^[a-z][0-9][a-z] ?[0-9][a-z][0-9]$/i.test(postalCode)) {
            errorsToDisplay.push("signup.errors.postalCode");
            return false;
        }
    
        return true;
      };

    return (
        <>
            <Row>
                <FormInput 
                    label="signup.firstNameEntry"
                    value={firstName}
                    onChange={(e) => setFirstName(e.target.value)}
                    errors={errors}
                    formError="signup.errors.firstName"
                    controlId="formBasicFirstName"
                />

                <FormInput 
                    label="signup.lastNameEntry"
                    value={lastName}
                    onChange={(e) => setLastName(e.target.value)}
                    errors={errors}
                    formError="signup.errors.lastName"
                    controlId="formBasicLastName"
                />
            </Row>
            
            <Row>
                <FormInput 
                    label="signup.addressEntry"
                    value={address}
                    onChange={(e) => setAddress(e.target.value)}
                    errors={errors}
                    formError="signup.errors.address"
                    controlId="formBasicAddress"
                />
            </Row>

            <Row>
                <FormInput 
                    label="signup.cityEntry"
                    value={city}
                    onChange={(e) => setCity(e.target.value)}
                    errors={errors}
                    formError="signup.errors.city"
                    controlId="formBasicCity"
                />

                <FormInput 
                    label="signup.postalCodeEntry"
                    value={postalCode}
                    onChange={(e) => setPostalCode(e.target.value)}
                    errors={errors}
                    formError="signup.errors.postalCode"
                    controlId="formBasicPostalCode"
                />
            </Row>

            <Row>
                <FormInput 
                    label="signup.phoneEntry"
                    value={phone}
                    onChange={(e) => setPhone(e.target.value)}
                    errors={errors}
                    formError="signup.errors.phone"
                    controlId="formBasicPhone"
                    placeholder="e.g. 5141234567"
                />
                    {extension !== undefined && setExtension !== undefined && (
                        <FormInput 
                            label="signup.extensionEntry"
                            value={extension}
                            onChange={(e) => setExtension(e.target.value)}
                            errors={errors}
                            formError="signup.errors.extension"
                            controlId="formBasicExtension"
                        />
                    )}
            </Row>

            <Row>
                <FormInput 
                    label="signup.emailEntry"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    errors={errors}
                    formError="signup.errors.email"
                    controlId="formBasicEmail"
                    type="email"
                />
            </Row>

            <Row>
                <FormInput 
                    label="signup.password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    errors={errors}
                    formError="signup.errors.password"
                    controlId="formBasicPassword"
                    type="password"
                />

                <FormInput 
                    label="signup.passwordConfirmation"
                    value={passwordConfirmation}
                    onChange={(e) => setPasswordConfirmation(e.target.value)}
                    errors={errors}
                    formError="signup.errors.passwordConfirmation"
                    controlId="formBasicPasswordConfirmation"
                    type="password"
                />
            </Row>

            <Button 
                variant="primary" 
                className="mt-3" 
                onClick={submitForm}
                disabled={isDisabled}
            >
                {t("signup.signup")}
            </Button>
            {unexpectedError !== "" && (
                <p className="unexpected-error">{unexpectedError}</p>
            )}
        </>
    );
}

export default Signup;