import { useState } from "react";
import { Form } from "react-bootstrap";
import { studentSignup } from "../../../services/signupService";
import { Student, User } from "../../../model/user";
import { useTranslation } from "react-i18next";
import { useParams } from "react-router";
import Signup from "..";
import FormError from "../../FormError";

const StudentSignup = () => {
  const {t} = useTranslation();
  const [studentId, setStudentId] = useState<string>("");
  const {depCode} = useParams();
  const [errors, setErrors] = useState<string[]>([]);

  const handleSubmit = (user: User) => {
    if (!depCode) return;

    let student: Student = {
      ...user,
      studentNumber: studentId,
    }

    studentSignup(student, depCode)
      .then((res) => {})
      .catch((err) => {});
  };

  const validateForm = (errors: string[]): boolean => {
    let isFormValid = true;
    let errorsToDisplay: string[] = [];

    if (!validateStudentId(errorsToDisplay)) isFormValid = false;
    setErrors([...errorsToDisplay, ...errors]);

    return isFormValid;
  };

  const validateStudentId = (errorsToDisplay: string[]): boolean => {
    if (studentId === "") {
      errorsToDisplay.push(t("signup.studentIdError"));
      return false;
    }

    return true;
  };

  return (
    <>
      <h3>{t("signup.studentFormTitle")}</h3>
      <Form>
        <Form.Group controlId="formBasicStudentId">
          <Form.Label>{t("signup.studentId")}</Form.Label>
          <Form.Control
            type="text"
            placeholder="Enter student ID"
            value={studentId}
            onChange={(e) => setStudentId(e.target.value)}
          />
        </Form.Group>

        <Signup
          handleSubmit={handleSubmit}
          setErrors={setErrors}
          validateExtraFormValues={validateForm}
        />
      </Form>
      <FormError errors={errors} />
    </>
  );
};

export default StudentSignup;
