import { useState } from "react";
import { Button, Col, Form, Row } from "react-bootstrap";
import { useTranslation } from "react-i18next";
import { User } from "../../model/user";
import { validateEmail, validatePassword } from "../../services/validationService";

interface Props {
    handleSubmit: (user: User) => void;
    extension?: string;
    setExtension?: (extension: string) => void;
    setErrors: (errors: string[]) => void;
    validateExtraFormValues?: (errorsToDisplay: string[]) => boolean;
}

const Signup = ({handleSubmit, extension, setExtension, setErrors, validateExtraFormValues}: Props) => {
    const {t} = useTranslation();
    const [firstName, setFirstName] = useState<string>("");
    const [lastName, setLastName] = useState<string>("");
    const [address, setAddress] = useState<string>("");
    const [city, setCity] = useState<string>("");
    const [postalCode, setPostalCode] = useState<string>("");
    const [phone, setPhone] = useState<string>("");
    const [email, setEmail] = useState<string>("");
    const [password, setPassword] = useState<string>("");
    const [passwordConfirmation, setPasswordConfirmation] = useState<string>("");

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

        handleSubmit(user);
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
                <Form.Group as={Col} controlId="formBasicPhone">
                    <Form.Label>{t("signup.phoneEntry")}</Form.Label>
                    <Form.Control
                    type="text"
                    value={phone}
                    onChange={(e) => setPhone(e.target.value)}
                    placeholder="e.g. 123-456-7890"
                    />
                </Form.Group>
                    {extension !== undefined && setExtension !== undefined && (
                        <Form.Group as={Col} controlId="formBasicExtension">
                            <Form.Label>{t("signup.extensionEntry")}</Form.Label>
                            <Form.Control
                            type="text"
                            value={extension}
                            onChange={(e) => setExtension(e.target.value)}
                            />
                        </Form.Group>
                    )}
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

            <Button variant="primary" className="mt-3" onClick={submitForm}>
                {t("signup.signup")}
            </Button>
        </>
    );
}

export default Signup;