import { useEffect, useState } from "react";
import { Container } from "react-bootstrap";
import { getStudentById } from "../../services/userService";
import { useTranslation } from "react-i18next";
import { Student, UserType } from "../../model/user";
import { Offer } from "../../model/offer";
import { getStudentOffersByDepartment } from "../../services/offerService";
import OffersList from "../../components/OffersList";
import { getUserId } from "../../services/authService";
import { useNavigate } from "react-router-dom";
import FileUploader from "../../components/FileUploader";
import {getCvsByStudentId} from "../../services/studentApplicationService";
import {CV} from "../../model/cv";
import CvList from "../../components/CVsList";

const StudentView = () => {
  const [student, setStudent] = useState<Student>();
  const [offers, setOffers] = useState<Offer[]>([]);
  const [error, setError] = useState<string>("");
  const [cvs, setCvs] = useState<CV[]>([]);
  const { t } = useTranslation();
  const navigate = useNavigate();


  useEffect(() => {
    if (student !== undefined) return;

    const id = getUserId();

    if (id == null) {
      navigate("/pageNotFound");
      return;
    }

    getStudentById(parseInt(id))
      .then((res) => {
        setStudent(res.data);
      })
      .catch((err) => {
        console.log(err);
        if (err.request.status === 404)
          setError(t("studentOffersList.errors.studentNotFound"));
      });
  }, [student, navigate, t]);

  useEffect(() => {
    if (student === undefined) return;

    getStudentOffersByDepartment(student.department!.id!)
      .then((res) => {
        setOffers(res.data);
      })
      .catch((err) => {
        console.log(err);
        if (err.request.status === 404)
          setError(t("offersList.errors.departmentNotFound"));
      });

    getCvsByStudentId(student.id!)
        .then((res) => {
          return setCvs(res.data);
        })
  }, [student, t]);

  return (
    <Container>
      <h1 className="my-3">Student view</h1>
      <h2>{t("studentOffersList.viewTitle")}</h2>
      <OffersList offers={offers} error={error} userType={UserType.Student} />
      <CvList></CvList>
      <FileUploader student={student!} />
    </Container>
  );
};

export default StudentView;
