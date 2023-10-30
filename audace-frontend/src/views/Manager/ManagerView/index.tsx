import { Button, Container } from "react-bootstrap";
import { useTranslation } from "react-i18next";
import { useNavigate } from "react-router-dom";
import {useEffect, useState} from "react";
import {
    getAcceptedApplicationsByDepartment,
    getDepartments,
    getStudentByAcceptedApplicationsByDepartment
} from "../../../services/offerService";
import {Department} from "../../../model/department";
import {Student} from "../../../model/user";
import {CVStatus} from "../../../model/cv";
import ManagerApplicationsList from "../../../components/ManagerApplicationsList";
import Application from "../../../model/application";

const ManagerView = () => {
  const navigate = useNavigate();
  const { t } = useTranslation();
  const [students, setStudents] = useState<Student[]>([]);
  const [departments, setDepartments] = useState<Department[]>([]);
  const [applications, setApplications] = useState<Application[]>([]);
  const [error, setError] = useState<string>("");
  const currentDepartmentId = 1;

  const tempStudent = {
      id: 1,
      firstName: "Chad",
      lastName: "Israd",
      email: "ChadIsRad@myspace.com",
      phone: "123456789",
      address: "1234 Street",
      password: "password",
      type: "student",
      studentNumber: "123456788",
      department: departments[0]
  }

  const tempCV = {
      id: 1,
      fileName: "ChadIsRadCV",
      content: "I am Chad and I am rad",
      student: tempStudent,
      cvStatus: CVStatus.ACCEPTED
  }

  students.push(tempStudent);

    useEffect(() => {
      getDepartments()
          .then((res) => {
              setDepartments(res.data);
              setError("");
          })
          .catch((err) => {
              setError(err.response.data);
              console.error("Departments error: " + err);
          });
      getAcceptedApplicationsByDepartment(currentDepartmentId)
        .then((res) => {
            setApplications(res.data);
            setError("");
        })
        .catch((err) => {
            setError(err.response.data);
            console.error("Applications error: " + err);
        });
      getStudentByAcceptedApplicationsByDepartment(currentDepartmentId)
          .then((res) => {
            setStudents(res);
            setError("");
          })
          .catch((err) => {
              setError(err.response.data);
              console.error("Students error: " + err);
          });
      console.log(students);
    }, []);

  const seeOffers = () => {
    navigate(`/manager/offers`);
  };
  const seeCvs = () => {
    navigate(`/manager/cvs`);
  };

  return (
    <Container>
      <h1>Manager view</h1>
      <Button onClick={seeOffers}>{t("manager.seeOffersButton")}</Button>
      <Button onClick={seeCvs}>{t("manager.seeCvsButton")}</Button>
      <ManagerApplicationsList applications={applications} error={error} />
    </Container>
  );
};

export default ManagerView;
