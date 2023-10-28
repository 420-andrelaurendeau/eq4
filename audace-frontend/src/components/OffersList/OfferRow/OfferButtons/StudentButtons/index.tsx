import { Button } from "react-bootstrap";
import { useTranslation } from "react-i18next";
import {
  apply,
  getApplicationsByStudentId,
  getCvsByStudentId,
} from "../../../../../services/studentApplicationService";
import { useEffect, useState } from "react";
import { getUserId } from "../../../../../services/authService";
import { Offer } from "../../../../../model/offer";
import Application from "../../../../../model/application";
import { useCVContext } from "../../../../../contextsholders/providers/CVContextHolder";
import { useApplicationContext } from "../../../../../contextsholders/providers/ApplicationsContextHolder";
import { useSessionContext } from "../../../../../contextsholders/providers/SessionContextHolder";

interface Props {
  disabled?: boolean;
  offer: Offer;
}

const StudentButtons = ({ disabled, offer }: Props) => {
  const { t } = useTranslation();
  const [applicationMessage, setApplicationMessage] = useState("");
  const [applicationMessageColor, setApplicationMessageColor] = useState("");
  const studentId = getUserId();
  const { cvs, setCvs } = useCVContext();
  const { applications, setApplications } = useApplicationContext();
  const { chosenSession } = useSessionContext();
  const [disabledButton, setdisabledButton] = useState<boolean>(
    disabled !== undefined ? disabled : false
  );

  useEffect(() => {
    if (disabledButton) return;
    if (studentId === undefined) return;

    applications.forEach((application) => {
      if (application.offer?.id === offer.id) {
        setdisabledButton(true);
      }
    });
  }, [studentId, applications, offer, disabledButton]);

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

    try {
      const applicationData: Application = {
        id: 1000,
        offer: offer,
        cv: cvs[0],
      };

      await apply(applicationData);

      handleApplicationsUpdate();

      setApplicationMessage(t("offersList.applicationMessageSuccess"));
      setApplicationMessageColor("green");
    } catch (error) {
      setApplicationMessage(t("offersList.applicationMessageFailure") + error);
      setApplicationMessageColor("red");
    }
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
      <Button disabled={disabledButton} onClick={handleApply}>
        {t("offersList.applyButton")}
      </Button>
      <p style={{ color: applicationMessageColor }}>{applicationMessage}</p>
    </div>
  );
};

export default StudentButtons;
