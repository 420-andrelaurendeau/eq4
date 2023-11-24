import { useTranslation } from "react-i18next";
import { useEffect, useState } from "react";
import { Offer } from "../../model/offer";
import { getUserId } from "../../services/authService";
import { useNavigate } from "react-router-dom";
import Application, { ApplicationStatus } from "../../model/application";
import { Container } from "react-bootstrap";
import { getAllApplicationsByEmployerIdAndOfferId } from "../../services/applicationService";
import ApplicationsList from "../ApplicationsList";

interface Props {
  offer: Offer;
  updateAvailablePlaces?: (offer: Offer) => void;
}

const Applications = ({ offer, updateAvailablePlaces }: Props) => {
  const [error, setError] = useState<string>("");
  const [applications, setApplications] = useState<Application[]>([]);
  const { t } = useTranslation();
  const navigate = useNavigate();

  useEffect(() => {
    const id = getUserId();
    if (id == null) {
      navigate("/pageNotFound");
      return;
    }
    getAllApplicationsByEmployerIdAndOfferId(parseInt(id), offer.id!)
      .then((res) => {
        setApplications(res.data);
      })
      .catch((err) => {
        setError(err.response.data);
        console.log(err);
      });
  }, [navigate, t, offer.id]);

  const updateApplicationsState = (
    application: Application,
    applicationStatus: ApplicationStatus
  ) => {
    let newApplications = applications.filter((a) => a.id !== application.id);
    application.applicationStatus = applicationStatus;
    newApplications.push(application);
    setApplications(newApplications);
    if (applicationStatus === ApplicationStatus.ACCEPTED)
      updateAvailablePlaces!(offer);
  };

  return (
    <Container>
      {
        <ApplicationsList
          offer={offer}
          applications={applications}
          error={error}
          updateApplicationsState={updateApplicationsState}
        />
      }
    </Container>
  );
};

export default Applications;
