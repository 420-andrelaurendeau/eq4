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

interface Props {
  offer: Offer;
}

const EmployerButtons = ({ offer }: Props) => {
  const { t } = useTranslation();
  const { setOffers } = useOfferContext();
  const { chosenSession } = useSessionContext();
  const navigate = useNavigate();

  const editButtonClick = (event: React.MouseEvent<HTMLElement>) => {
    event.stopPropagation();
    console.log("Edit button clicked");
    console.log("Offer:", offer);

    if (offer?.id) {
      navigate(`/employer/offers/${offer.id}`);
    } else {
      console.error("No offerId provided");
    }
  };

  const deleteButtonClick = async (event: React.MouseEvent<HTMLElement>) => {
    event.stopPropagation();
    console.log("Delete button clicked");
    console.log("Offer:", offer);

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
      console.log("Offer deleted successfully");
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
        className="ms-2 text-dark"
      >
        {t("employerOffersList.applicationButton")}
      </Button>
    </>
  );
};

export default EmployerButtons;