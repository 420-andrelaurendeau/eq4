import { Container } from "react-bootstrap";
import { Manager } from "../../../model/user";
import { useTranslation } from "react-i18next";
import { useEffect, useState } from "react";
import { getManagerCvsByDepartment } from "../../../services/cvService";
import { getManagerById } from "../../../services/userService";
import { getUserId } from "../../../services/authService";
import { useNavigate } from "react-router-dom";
import { CV, CVStatus } from "../../../model/cv";
import CvsList from "../../../components/CVsList";
import { useSessionContext } from "../../../contextsholders/providers/SessionContextHolder";
import SessionSelector from "../../../components/SessionSelector";

const ManagerCvView = () => {
  const [manager, setManager] = useState<Manager>();
  const [cvs, setCvs] = useState<CV[]>([]);
  const [cvsAccepted, setCvsAccepted] = useState<CV[]>([]);
  const [cvsRefused, setCvsRefused] = useState<CV[]>([]);
  const [error, setError] = useState<string>("");
  const { t } = useTranslation();
  const navigate = useNavigate();
  const { chosenSession } = useSessionContext();

  useEffect(() => {
    if (manager !== undefined) return;

    const id = getUserId();

    if (id == null) {
      navigate("/pageNotFound");
      return;
    }

    getManagerById(parseInt(id!))
      .then((res) => {
        setManager(res.data);
      })
      .catch((err) => {
        console.log(err);
        if (err.request.status === 404)
          setError(t("managerCvsList.errors.managerNotFound"));
      });
  }, [manager, t, navigate]);

  useEffect(() => {
    if (manager === undefined) return;
    if (chosenSession === undefined) return;

    getManagerCvsByDepartment(manager.department!.id!, chosenSession.id)
      .then((res) => {
        let acceptedCvs = [];
        let refusedCvs = [];
        let cvs = [];
        for (let i = 0; i < res.data.length; i = i + 1) {
          if (res.data[i].cvStatus === "ACCEPTED") {
            acceptedCvs.push(res.data[i]);
          } else if (res.data[i].cvStatus === "REFUSED") {
            refusedCvs.push(res.data[i]);
          } else if (res.data[i].cvStatus === "PENDING") {
            cvs.push(res.data[i]);
          }
        }
        setCvsAccepted(acceptedCvs);
        setCvsRefused(refusedCvs);
        setCvs(cvs);
      })
      .catch((err) => {
        console.log(err);
        if (err.request.status === 404)
          setError(t("cvsList.errors.departmentNotFound"));
      });
  }, [manager, t, chosenSession]);

  const updateCvsState = (cv: CV, cvStatus: CVStatus) => {
    let newCvs = cvs.filter((c) => c.id !== cv.id);
    cv.cvStatus = cvStatus;
    setCvs(newCvs);
    if (cvStatus === "ACCEPTED") {
      setCvsAccepted([...cvsAccepted, cv]);
    } else if (cvStatus === "REFUSED") {
      setCvsRefused([...cvsRefused, cv]);
    }
  };
  return (
    <Container>
      <h1 className="mt-3">{t("managerCvsList.viewTitle")}</h1>

      <SessionSelector />

      {cvs.length > 0 ? (
          <Container style={{ padding: 0 }}>
            <h4>{t("managerCvsList.pendingCvs")}</h4>
            <CvsList cvs={cvs} error={error} updateCvsState={updateCvsState} />
          </Container>
      ) : (
        <p>{t("managerCvsList.noMorePendingCvs")}</p>
      )}
      {cvsAccepted.length > 0 ? (
          <Container style={{ padding: 0 }}>
            <h4>{t("managerCvsList.acceptedCvs")}</h4>
            <CvsList cvs={cvsAccepted} error={error} />
          </Container>
      ) : null}
      {cvsRefused.length > 0 ? (
          <Container style={{ padding: 0 }}>
            <h4>{t("managerCvsList.refusedCvs")}</h4>
            <CvsList cvs={cvsRefused} error={error} />
          </Container>
      ) : null}
    </Container>
  );
};

export default ManagerCvView;
