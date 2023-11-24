import { useState } from "react";
import { Offer, OfferStatus } from "../../../model/offer";
import OfferModal from "./OfferModal";
import "./styles.css";
import { formatDate } from "../../../services/formatService";
import OfferButtons from "./OfferButtons";

import { Col } from "react-bootstrap";
import { useTranslation } from "react-i18next";
import { useSessionContext } from "../../../contextsholders/providers/SessionContextHolder";

interface Props {
  offer: Offer;
  updateOffersState?: (offer: Offer, offerStatus: OfferStatus) => void;
}

const OfferRow = ({ offer, updateOffersState }: Props) => {
  const [show, setShow] = useState<boolean>(false);
  const { t } = useTranslation();
  const { currentSession, chosenSession, nextSession } = useSessionContext();


  const handleClick = () => setShow(true);
  const handleClose = () => setShow(false);

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
        {chosenSession?.id === nextSession?.id && (
          <td>
            <div className="d-flex justify-content-center">
              <OfferButtons
                offer={offer}
                updateOffersState={updateOffersState}
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
          employer={offer.employer}
          updateOffersState={updateOffersState}
        />
      )}
    </>
  );
};

export default OfferRow;
