import { useState } from "react";
import { Button, Form } from "react-bootstrap";
import { studentSignup } from "../../../services/signupService";
import { Student } from "../../../model/user";
import { useTranslation } from "react-i18next";

const StudentSignup = () => {
  const {t} = useTranslation();
  const [email, setEmail] = useState<string>("");
  const [studentId, setStudentId] = useState<string>("");
  const [password, setPassword] = useState<string>("");

  const handleSubmit = () => {
    if (!validateForm()) return;

    // TODO: add hashing for password

    let student: Student = {
      email: email,
      studentId: studentId,
      password: password,
    };

    studentSignup(student)
      .then((res) => {
        console.log("Yaintizit")
      })
      .catch((err) => {
        console.log(err);
      });
  };

  const validateForm = (): boolean => {
    return validateEmail() && validateStudentId() && validatePassword();
  };

  const validateEmail = (): boolean => {
    return email !== "";
  };

  const validateStudentId = (): boolean => {
    return studentId !== "";
  };

  const validatePassword = (): boolean => {
    return password !== "";
  };

  return (
    <>
      <h3>{t("signup.studentFormTitle")}</h3>
      <Form>
        <Form.Group controlId="formBasicEmail">
          <Form.Label>{t("signup.emailEntry")}</Form.Label>
          <Form.Control
            type="email"
            placeholder={t("signup.emailEntry")}
            value={email}
            onChange={(e) => setEmail(e.target.value)}
          />
        </Form.Group>

        <Form.Group controlId="formBasicStudentId">
          <Form.Label>{t("signup.studentId")}</Form.Label>
          <Form.Control
            type="text"
            placeholder="Enter student ID"
            value={studentId}
            onChange={(e) => setStudentId(e.target.value)}
          />
        </Form.Group>

        <Form.Group controlId="formBasicPassword">
          <Form.Label>{t("signup.password")}</Form.Label>
          <Form.Control
            type="password"
            placeholder="Password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
        </Form.Group>

        <Button
          variant="primary"
          className="mt-3"
          onClick={handleSubmit}
        >
          {t("signup.signup")}
        </Button>
      </Form>
    </>
  );
};

export default StudentSignup;
