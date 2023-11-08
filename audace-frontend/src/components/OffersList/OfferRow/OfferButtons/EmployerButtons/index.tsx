import { Button } from "react-bootstrap";
import { useTranslation } from "react-i18next";
import { useState } from "react";
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
  disabled: boolean;
  seeApplications?: (offer: Offer) => void;
  offer: Offer;
}

const EmployerButtons = ({ disabled, seeApplications, offer }: Props) => {
  const { t } = useTranslation();
  const navigate = useNavigate();
  const { setOffers } = useOfferContext();
  const { chosenSession } = useSessionContext();

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
      console.log("Offer deleted successfully");
    } catch (error) {
      console.error("Failed to delete offer:", error);
    }
  };

  const seeApplicationsButtonClick = (event: React.MouseEvent<HTMLElement>) => {
    event.stopPropagation();
    seeApplications!(offer);
  };

  return (
    <>
      <Button
        disabled={disabled}
        onClick={editButtonClick}
        variant="outline-warning"
        className="me-2 text-dark"
      >
        {t("employerOffersList.editButton")}
      </Button>
      <Button
        disabled={disabled}
        onClick={deleteButtonClick}
        variant="outline-danger text-dark"
      >
        {t("employerOffersList.deleteButton")}
      </Button>
      {seeApplications !== undefined ? (
        <Button
          onClick={seeApplicationsButtonClick}
          variant="outline-success"
          className="ms-2 text-dark"
        >
          {t("employerOffersList.applicationButton")}
        </Button>
      ) : null}
    </>
  );
};

export default EmployerButtons;
