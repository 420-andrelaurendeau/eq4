import { Offer, OfferStatus } from "../../../../model/offer";
import { Employer } from "../../../../model/user";
import { Col, Modal, Row } from "react-bootstrap";
import { useTranslation } from "react-i18next";
import { formatDate } from "../../../../services/formatService";
import OfferButtons from "../OfferButtons";
import { useSessionContext } from "../../../../contextsholders/providers/SessionContextHolder";

interface Props {
  offer: Offer;
  show: boolean;
  handleClose: () => void;
  employer: Employer;
  updateOffersState?: (offer: Offer, offerStatus: OfferStatus) => void;
  disabled: boolean;
}

const OfferModal = ({
  offer,
  show,
  handleClose,
  employer,
  updateOffersState,
  disabled,
}: Props) => {
  const { t } = useTranslation();
  const { currentSession, chosenSession } = useSessionContext();

  const createBoldText = (text: string) => {
    return <b>{text}</b>;
  };

  return (
    <>
      <Modal show={show} onHide={handleClose} size="lg">
        <Modal.Header closeButton>
          <Modal.Title>{offer.title}</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Row>
            <Col>
              <div>
                {t("offer.modal.org")}: {createBoldText(employer.organisation)}
              </div>
              <div>
                {t("offer.modal.address")}:&nbsp;
                {createBoldText(employer.address!)}
              </div>
              <div>
                {t("offer.modal.phone")}:&nbsp;
                {createBoldText(employer.phone!)}
              </div>
            </Col>
            <Col>
              <div className="text-sm-end mt-2">
                <div>
                  {t("offer.modal.internDate.start")}:&nbsp;
                  {createBoldText(formatDate(offer.internshipStartDate))}&nbsp;
                  {t("offer.modal.internDate.end")}:&nbsp;
                  {createBoldText(formatDate(offer.internshipEndDate))}
                </div>
                <div>
                  {t("offer.modal.offerEnd")}:{" "}
                  {createBoldText(formatDate(offer.offerEndDate))}
                </div>
              </div>
            </Col>
          </Row>
          <hr />
          <div style={{ textAlign: "justify" }}>{offer.description}</div>
        </Modal.Body>
        {chosenSession?.id === currentSession?.id && (
          <Modal.Footer>
            <OfferButtons
              disabled={disabled}
              offer={offer}
              updateOffersState={updateOffersState}
            />
          </Modal.Footer>
        )}
      </Modal>
    </>
  );
};

export default OfferModal;
