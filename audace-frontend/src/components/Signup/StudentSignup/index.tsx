import { useState } from "react";
import { Form } from "react-bootstrap";
import { studentSignup } from "../../../services/signupService";
import { Student, User } from "../../../model/user";
import { useTranslation } from "react-i18next";
import { useParams } from "react-router";
import Signup from "..";
import FormInput from "../FormInput";

const StudentSignup = () => {
  const {t} = useTranslation();
  const [studentId, setStudentId] = useState<string>("");
  const {depCode} = useParams();
  const [errors, setErrors] = useState<string[]>([]);

  const handleSubmit = (user: User) => {
    let student: Student = {
      ...user,
      studentNumber: studentId,
      type: "student",
    }

    return studentSignup(student, depCode!);
  };

  const validateForm = (errors: string[]): boolean => {
    if (!depCode) return false;

    let isFormValid = true;
    let errorsToDisplay: string[] = [];

    if (!validateStudentId(errorsToDisplay)) isFormValid = false;
    setErrors([...errorsToDisplay, ...errors]);

    return isFormValid;
  };

  const validateStudentId = (errorsToDisplay: string[]): boolean => {
    if (studentId === "") {
      errorsToDisplay.push("signup.errors.studentId");
      return false;
    }

    return true;
  };

  return (
    <>
      <h3>{t("signup.studentFormTitle")}</h3>
      <Form>
        <FormInput 
          label="signup.studentId"
          value={studentId}
          onChange={(e) => setStudentId(e.target.value)}
          errors={errors}
          formError="signup.errors.studentId"
          controlId="formBasicStudentId"
        />

        <Signup
          handleSubmit={handleSubmit}
          errors={errors}
          setErrors={setErrors}
          validateExtraFormValues={validateForm}
        />
      </Form>
    </>
  );
};

export default StudentSignup;
