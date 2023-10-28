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
import { useCVContext } from "../../../contextsholders/providers/CVContextHolder";
import { useSessionContext } from "../../../contextsholders/providers/SessionContextHolder";
import CvsList from "../../../components/CvsList";
import SessionSelector from "../../../components/SessionSelector";

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
  const { setCvs, cvs } = useCVContext();
  const { chosenSession } = useSessionContext();

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
    if (chosenSession === undefined) return;

    getStudentOffersByDepartment(student.department!.id!, chosenSession.id)
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
  }, [student, t, setCvs, chosenSession]);

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

      <SessionSelector />

      {viewOffers && (
        <>
          <OffersList
            offers={offers}
            error={offersError}
            userType={UserType.Student}
          />
        </>
      )}
      <CvsList error={cvsError} cvs={cvs} userType={UserType.Student} />
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
