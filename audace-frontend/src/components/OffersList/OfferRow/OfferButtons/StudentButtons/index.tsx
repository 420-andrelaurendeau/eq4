import { Button } from "react-bootstrap";
import { useTranslation } from "react-i18next";
import {
  studentApplyToOffer,
  getApplicationsByStudentId,
} from "../../../../../services/applicationService";
import { useEffect, useState } from "react";
import { getUserId } from "../../../../../services/authService";
import { Offer } from "../../../../../model/offer";
import Application from "../../../../../model/application";
import { useCVContext } from "../../../../../contextsholders/providers/CVContextHolder";
import { useApplicationContext } from "../../../../../contextsholders/providers/ApplicationsContextHolder";
import { useSessionContext } from "../../../../../contextsholders/providers/SessionContextHolder";
import { getCvsByStudentId } from "../../../../../services/cvService";

interface Props {
  disabled?: boolean;
  offer: Offer;
}

const StudentButtons = ({ disabled, offer }: Props) => {
  const { t } = useTranslation();
  const [applicationMessage, setApplicationMessage] = useState("");
  const [applicationMessageColor, setApplicationMessageColor] = useState("");
  const [error, setError] = useState<string>("");
  const studentId = getUserId();
  const { cvs, setCvs } = useCVContext();
  const { applications, setApplications } = useApplicationContext();
  const { chosenSession } = useSessionContext();

  const isButtonDisabled = (): boolean => {
    if (disabled) return true;
    if (applications === undefined || cvs === undefined || cvs.length === 0)
      return true;

    return (
      applications.filter((application) => application.offer?.id === offer.id)
        .length > 0
    );
  };

  useEffect(() => {
    if (studentId === undefined) return;

    getCvsByStudentId(parseInt(studentId!))
      .then((res) => {
        setCvs(res.data);
      })
      .catch((err) => {
        console.log("getCvsByStudentId error", err);
      });
  }, [studentId, setCvs]);

  const handleApply = async (event: { stopPropagation: () => void }) => {
    event.stopPropagation();
    if (!studentId || !cvs) {
      throw new Error("Student/CV null");
    }

    const applicationData: Application = {
      offer: offer,
      cv: cvs[0],
    };

    studentApplyToOffer(applicationData)
      .then(() => {
        handleApplicationsUpdate();
        setError("");
        setApplicationMessage("offersList.applicationMessageSuccess");
        setApplicationMessageColor("green");
      })
      .catch((err) => {
        setApplicationMessage("offersList.applicationMessageFailure");
        setError(err);
        setApplicationMessageColor("red");
      });
  };

  const handleApplicationsUpdate = () => {
    getApplicationsByStudentId(parseInt(studentId!), chosenSession?.id!)
      .then((res) => {
        setApplications(res.data);
      })
      .catch((err) => {
        console.log("getApplicationsByStudentId error", err);
      });
  };

  return (
    <div
      style={{
        display: "flex",
        flexDirection: "column",
        alignItems: "center",
        justifyContent: "center",
      }}
    >
      <Button
        disabled={isButtonDisabled()}
        onClick={handleApply}
        variant="outline-primary"
        className="text-dark"
      >
        {t("offersList.applyButton")}
      </Button>
      <p style={{ color: applicationMessageColor }}>
        {`${t(applicationMessage)} ${error}`}
      </p>
    </div>
  );
};

export default StudentButtons;
