import { useEffect, useState } from "react";
import { Offer, OfferStatus } from "../../../model/offer";
import OfferModal from "./OfferModal";
import "./styles.css";
import { formatDate } from "../../../services/formatService";
import OfferButtons from "./OfferButtons";

import { Card, Col } from "react-bootstrap";
import { useTranslation } from "react-i18next";
import { useSessionContext } from "../../../contextsholders/providers/SessionContextHolder";
import Application, { ApplicationStatus } from "../../../model/application";
import { UserType } from "../../../model/user";
import Accordion from "react-bootstrap/Accordion";
import Applications from "../../Applications";
import { getUserId, getUserType } from "../../../services/authService";
import { getAllApplicationsByEmployerIdAndOfferId } from "../../../services/applicationService";
import { useNavigate } from "react-router-dom";

interface Props {
  offer: Offer;
  updateOffersState?: (offer: Offer, offerStatus: OfferStatus) => void;
  updateAvailablePlaces?: (offer: Offer) => void;
}

const OfferRow = ({
  offer,
  updateOffersState,
  updateAvailablePlaces,
}: Props) => {
  const [show, setShow] = useState<boolean>(false);
  const { t } = useTranslation();
  const [error, setError] = useState<string>("");
  const { currentSession, chosenSession } = useSessionContext();
  const [applications, setApplications] = useState<Application[]>([]);
  const [pendingApplications, setPendingApplications] =
    useState<Application[]>();
  const userType = getUserType();
  const navigate = useNavigate();

  useEffect(() => {
    const id = getUserId();
    if (id == null) {
      navigate("/pageNotFound");
      return;
    }
    if (userType === UserType.Employer) {
      getAllApplicationsByEmployerIdAndOfferId(parseInt(id), offer.id!)
        .then((res) => {
          setApplications(res.data);
          setPendingApplications(
            res.data.filter(
              (a) => a.applicationStatus === ApplicationStatus.PENDING
            )
          );
        })
        .catch((err) => {
          setError(err.response.data);
          console.log(err);
        });
    }
  }, [navigate, t, offer.id, userType]);

  const handleClick = () => setShow(true);
  const handleClose = () => setShow(false);
  const updateApplicationsState = (
    application: Application,
    applicationStatus: ApplicationStatus
  ) => {
    let newApplications = applications.filter((a) => a.id !== application.id);

    application.applicationStatus = applicationStatus;
    newApplications.push(application);

    let newPendingApplications = newApplications.filter(
      (a) => a.applicationStatus === ApplicationStatus.PENDING
    );

    setApplications(newApplications);
    setPendingApplications(newPendingApplications);

    if (applicationStatus === ApplicationStatus.ACCEPTED)
      updateAvailablePlaces!(offer);
  };

  return (
    <>
      <tr>
        <td>
          <Col className="h5">{offer.title}</Col>
          <Col className="text-muted small mt-2">
            <u className="hovered" onClick={handleClick}>
              {t("offersList.viewMore")}
            </u>
          </Col>
        </td>
        <td>{formatDate(offer.internshipStartDate)}</td>
        <td>{formatDate(offer.internshipEndDate)}</td>
        <td>{offer.availablePlaces}</td>
        {chosenSession?.id === currentSession?.id && (
          <td>
            <div className="d-flex justify-content-center">
              <OfferButtons
                offer={offer}
                updateOffersState={updateOffersState}
                pendingApplications={pendingApplications}
              />
            </div>
          </td>
        )}
      </tr>
      {userType === UserType.Employer && (
        <tr>
          <td colSpan={12}>
            <Accordion.Collapse eventKey={offer.id!.toString()}>
              <Card.Body>
                <Applications
                  error={error}
                  offer={offer}
                  applications={applications}
                  updateAvailablePlaces={updateAvailablePlaces}
                  updateApplicationsState={updateApplicationsState}
                />
              </Card.Body>
            </Accordion.Collapse>
          </td>
        </tr>
      )}
      {show && (
        <OfferModal
          offer={offer}
          show={show}
          handleClose={handleClose}
          employer={offer.employer}
          updateOffersState={updateOffersState}
        />
      )}
    </>
  );
};

export default OfferRow;
