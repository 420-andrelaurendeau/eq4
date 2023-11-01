import { Button } from "react-bootstrap";
import { useTranslation } from "react-i18next";
import { useState } from "react";
import { Offer } from "../../../../../model/offer";
import { useNavigate } from "react-router-dom";
import { employerDeleteOffer } from "../../../../../services/offerService";

interface Props {
  disabled: boolean;
  seeApplications?: (offer: Offer) => void;
  offer: Offer;
  hideRow?: () => void;
}

const EmployerButtons = ({
  disabled,
  seeApplications,
  offer,
  hideRow,
}: Props) => {
  const { t } = useTranslation();
  const [isDeleting, setIsDeleting] = useState(false);
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

    if (offer === undefined || offer.id === undefined) {
      return;
    }

    hideRow!();
    setIsDeleting(true);

    try {
      const response = await employerDeleteOffer(offer.id);

      if (response.status !== 200) {
        throw new Error(`Failed to delete offer. Status: ${response.status}`);
      }
      console.log("Offer deleted successfully");
    } catch (error) {
      console.error("Failed to delete offer:", error);
    } finally {
      setIsDeleting(false);
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
        disabled={disabled || isDeleting}
        onClick={deleteButtonClick}
        variant="outline-danger text-dark"
      >
        {isDeleting
          ? t("employerOffersList.deletingButton")
          : t("employerOffersList.deleteButton")}
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
