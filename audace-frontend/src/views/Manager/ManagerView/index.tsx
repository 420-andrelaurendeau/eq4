import { Button, Container } from "react-bootstrap";
import { useTranslation } from "react-i18next";
import { useNavigate } from "react-router-dom";
import {useEffect, useState} from "react";
import {getDepartments, getStudentByAcceptedApplicationsByDepartment} from "../../../services/offerService";
import {Department} from "../../../model/department";
import {Student} from "../../../model/user";

const ManagerView = () => {
  const navigate = useNavigate();
  const { t } = useTranslation();
  const [students, setStudents] = useState<Student[]>([]);
  const [departments, setDepartments] = useState<Department[]>([]);
   const currentDepartmentId = 1;

    useEffect(() => {
      getDepartments()
          .then((res) => {
            setDepartments(res.data);
          }
            ).catch((err) => {
                console.error("Departments error: " + err);
            });
      getStudentByAcceptedApplicationsByDepartment(currentDepartmentId)
          .then((res) => {
            setStudents(res);
          })
          .catch((err) => {
              console.error("Students error: " + err);
          });
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
    </Container>
  );
};

export default ManagerView;
