import { useState } from "react";
import { Offer, OfferStatus } from "../../../model/offer";
import OfferModal from "./OfferModal";
import "./styles.css";
import { formatDate } from "../../../services/formatService";
import { Employer, UserType } from "../../../model/user";
import OfferButtons from "./OfferButtons";

import { Col } from "react-bootstrap";
import { useTranslation } from "react-i18next";
import { useSessionContext } from "../../../contextsholders/providers/SessionContextHolder";

interface Props {
  offer: Offer;
  userType: UserType;
  updateOffersState?: (offer: Offer, offerStatus: OfferStatus) => void;
  seeApplications?: (offer: Offer) => void;
}

const OfferRow = ({
  offer,
  userType,
  updateOffersState,
  seeApplications,
}: Props) => {
  const [show, setShow] = useState<boolean>(false);
  const [employer, setEmployer] = useState<Employer>(offer.employer);
  const { t } = useTranslation();
  const { currentSession, chosenSession } = useSessionContext();
  const [disabled, setDisabled] = useState<boolean>(true);
  const [isVisible, setIsVisible] = useState(true);

  const handleClick = () => {
    setShow(true);
    setDisabled(false);
  };

  const handleClose = () => setShow(false);
  const hideRow = () => setIsVisible(false);

  if (!isVisible) return null;

  return (
    <>
      <tr>
        <td>
          <Col className="h5">{offer.title}</Col>
          <Col className="text-muted small mt-2">
            <u className="hovered" onClick={handleClick}>{t("offersList.viewMore")}</u>
          </Col>
        </td>
        <td>{formatDate(offer.internshipStartDate)}</td>
        <td>{formatDate(offer.internshipEndDate)}</td>
        <td>{offer.availablePlaces}</td>
        {chosenSession?.id === currentSession?.id && (
          <td>
            <div className="d-flex justify-content-center">
              <OfferButtons
                userType={userType}
                disabled={disabled}
                offer={offer}
                updateOffersState={updateOffersState}
                seeApplications={seeApplications}
                hideRow={hideRow}
              />
            </div>
          </td>
        )}
      </tr>
      {show && (
        <OfferModal
          offer={offer}
          show={show}
          handleClose={handleClose}
          userType={userType}
          employer={employer}
          setEmployer={setEmployer}
          updateOffersState={updateOffersState}
          disabled={disabled}
          hideRow={hideRow}
        />
      )}
    </>
  );
};

export default OfferRow;
