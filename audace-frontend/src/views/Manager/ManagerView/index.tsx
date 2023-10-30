import { Button, Container } from "react-bootstrap";
import { useTranslation } from "react-i18next";
import { useNavigate } from "react-router-dom";
import {useEffect, useState} from "react";
import {
    getAcceptedApplicationsByDepartment,
    getDepartments,
    getStudentByApplication
} from "../../../services/offerService";
import {Department} from "../../../model/department";
import {Employer} from "../../../model/user";
import {CVStatus} from "../../../model/cv";
import ManagerApplicationsList from "../../../components/ManagerApplicationsList";
import Application, {ApplicationStatus} from "../../../model/application";
import {Offer, OfferStatus} from "../../../model/offer";

const ManagerView = () => {
  const navigate = useNavigate();
  const { t } = useTranslation();
  const [departments, setDepartments] = useState<Department[]>([]);
  const [applications, setApplications] = useState<Application[]>([]);
  const [error, setError] = useState<string>("");
  const currentDepartmentId = 1;
  const [tempApplications] = useState<Application[]>([]);

  const tempTest = () => {
      if (applications.length == 3) {
          return;
      }
      const tempEmployer: Employer = {
          id: 2,
          firstName: "Chad",
          lastName: "Israd",
          email: "ChadIsRad@myspace.com",
          phone: "123456789",
          address: "1234 Street",
          password: "password",
          type: "employer",
          organisation: "Chad's Smoke Store",
          position: "Chad",
          extension: "1234"
      }
      const tempStudent = {
          id: 1,
          firstName: "Chad",
          lastName: "Is Rad",
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

      const tempOffer: Offer = {
          id: 1,
          title: "Vape tester at the raddest vape shop in m-town",
          description: "required to vape and be rad",
          internshipStartDate: new Date(),
          internshipEndDate: new Date(),
          offerEndDate: new Date(),
          availablePlaces: 1,
          employer: tempEmployer,
          department: departments[0],
          offerStatus: OfferStatus.ACCEPTED
      }

      const tempApplication: Application = {
          id: 1,
          student: tempStudent,
          offer: tempOffer,
          cv: tempCV,
          applicationStatus: ApplicationStatus.ACCEPTED
      }
      tempApplications.push(tempApplication);
  }

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
        retrieveApplicationStudents();
      tempTest();
      console.log("applications:" + tempApplications);
    }, []);

  const retrieveApplicationStudents = () => {
        applications.forEach((application) => {
            getStudentByApplication(application)
                .then((res) => {
                    application.student = res.data;
                    setError("");
                })
                .catch((err) => {
                    setError(err.response.data);
                    console.error("Students error: " + err);
                });
        })
  }

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
      <ManagerApplicationsList applications={tempApplications} error={error} />
    </Container>
  );
};

export default ManagerView;
