import { Button, useAccordionButton } from "react-bootstrap";
import { useTranslation } from "react-i18next";
import { Offer } from "../../../../../model/offer";
import { useNavigate } from "react-router-dom";
import {
  employerDeleteOffer,
  getAllOffersByEmployerIdAndSessionId,
} from "../../../../../services/offerService";
import { useOfferContext } from "../../../../../contextsholders/providers/OfferContextHolder";
import { getUserId } from "../../../../../services/authService";
import { useSessionContext } from "../../../../../contextsholders/providers/SessionContextHolder";
import Application from "../../../../../model/application";

interface Props {
  offer: Offer;
  pendingApplications?: Application[];
}

const EmployerButtons = ({ offer, pendingApplications }: Props) => {
  const { t } = useTranslation();
  const { setOffers } = useOfferContext();
  const { chosenSession } = useSessionContext();
  const navigate = useNavigate();

  const editButtonClick = (event: React.MouseEvent<HTMLElement>) => {
    event.stopPropagation();

    if (offer?.id) {
      navigate(`/employer/offers/${offer.id}`);
    } else {
      console.error("No offerId provided");
    }
  };

  const deleteButtonClick = async (event: React.MouseEvent<HTMLElement>) => {
    event.stopPropagation();

    try {
      const response = await employerDeleteOffer(offer.id!);

      const offersResponse = await getAllOffersByEmployerIdAndSessionId(
        parseInt(getUserId()!),
        chosenSession!.id
      );

      setOffers(offersResponse.data);

      if (response.status !== 200) {
        throw new Error(`Failed to delete offer. Status: ${response.status}`);
      }
    } catch (error) {
      console.error("Failed to delete offer:", error);
    }
  };
  const seeApplicationsButtonClick = useAccordionButton(offer.id!.toString());

  return (
    <>
      <Button
        onClick={editButtonClick}
        variant="outline-warning"
        className="me-2 text-dark"
      >
        {t("employerOffersList.editButton")}
      </Button>
      <Button onClick={deleteButtonClick} variant="outline-danger text-dark">
        {t("employerOffersList.deleteButton")}
      </Button>
      <Button
        onClick={seeApplicationsButtonClick}
        variant="outline-success"
        className="ms-2 text-dark position-relative"
      >
        {t("employerOffersList.applicationButton")}
        {offer.availablePlaces > 0 &&
          pendingApplications &&
          pendingApplications.length > 0 && (
            <span className="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger">
              {pendingApplications.length}
            </span>
          )}
      </Button>
    </>
  );
};

export default EmployerButtons;
