import { useEffect, useState } from "react";
import { Container } from "react-bootstrap";
import { getStudentById } from "../../../services/userService";
import { useTranslation } from "react-i18next";
import { Student, UserType } from "../../../model/user";
import { Offer } from "../../../model/offer";
import { getStudentOffersByDepartment } from "../../../services/offerService";
import OffersList from "../../../components/OffersList";
import { getUserId } from "../../../services/authService";
import { useNavigate } from "react-router-dom";
import FileUploader from "../../../components/FileUploader";
import { getCvsByStudentId } from "../../../services/studentApplicationService";
import CvsList from "../../../components/CVsListStudent";
import { useCVContext } from "../../../contextsholders/providers/CVContextHolder";

interface StudentViewProps {
  viewOffers?: boolean;
  viewUpload?: boolean;
}

const StudentView = ({
  viewOffers = true,
  viewUpload = true,
}: StudentViewProps) => {
  const [student, setStudent] = useState<Student>();
  const [offers, setOffers] = useState<Offer[]>([]);
  const [offersError, setOffersError] = useState<string>("");
  const [cvsError, setCvsError] = useState<string>("");
  const { t } = useTranslation();
  const navigate = useNavigate();
  const { setCvs } = useCVContext();

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
        console.error(err);
        if (err.request && err.request.status === 404) {
          setOffersError(t("studentOffersList.errors.studentNotFound"));
          setCvsError(t("studentOffersList.errors.studentNotFound"));
        }
      });
  }, [student, navigate, t]);

  useEffect(() => {
    if (student === undefined) return;

    getStudentOffersByDepartment(student.department!.id!)
      .then((res) => {
        setOffers(res.data);
      })
      .catch((err) => {
        console.error(err);

        if (err.request && err.request.status === 404) {
          setOffersError(t("offersList.errors.departmentNotFound"));
        }
      });

    getCvsByStudentId(student.id!)
      .then((res) => {
        setCvs(res.data);
      })
      .catch((err) => {
        console.error(err);
      });
  }, [student, t, setCvs]);

  const handleUploadSuccess = () => {
    getCvsByStudentId(student!.id!)
      .then((res) => {
        setCvs(res.data);
      })
      .catch((err) => {
        console.error(err);
      });
  };

  return (
    <Container>
      <h1 className="my-3" style={{ textTransform: "capitalize" }}>
        {student?.firstName} {student?.lastName}
      </h1>
      {viewOffers && (
        <>
          <h2>{t("studentOffersList.viewTitle")}</h2>
          <OffersList
            offers={offers}
            error={offersError}
            userType={UserType.Student}
          />
        </>
      )}
      <CvsList error={cvsError} />
      {viewUpload && (
        <FileUploader
          student={student!}
          onUploadSuccess={handleUploadSuccess}
        />
      )}
    </Container>
  );
};

export default StudentView;
