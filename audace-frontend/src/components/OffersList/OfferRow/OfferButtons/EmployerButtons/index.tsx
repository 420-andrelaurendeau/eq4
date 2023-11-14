import { Button, useAccordionButton } from "react-bootstrap";
import { useTranslation } from "react-i18next";
import { Offer } from "../../../../../model/offer";
import { useNavigate } from 'react-router-dom';
import {useState} from "react";
import {employerDeleteOffer} from "../../../../../services/offerService";
interface Props {
  disabled?: boolean;
  offer: Offer;
  hideRow?: () => void;
}

const EmployerButtons = ({ disabled,  offer, hideRow }: Props) => {
  const {t} = useTranslation();
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
      if (response.status !== 200) throw new Error(`Failed to delete offer. Status: ${response.status}`);
      console.log("Offer deleted successfully");
    } catch (error) {
        console.error("Failed to delete offer:", error);
    } finally {
        setIsDeleting(false);
    }
  };
  const seeApplicationsButtonClick = useAccordionButton(offer.id!.toString());

  return (
      <>
          <Button disabled={disabled} onClick={editButtonClick} className="btn-light btn-outline-warning text-dark">
              {t("employerOffersList.editButton")}
          </Button>
          <Button disabled={disabled || isDeleting} onClick={deleteButtonClick} className="btn-light btn-outline-danger text-dark ms-2">
              {isDeleting ? t("employerOffersList.deletingButton") : t("employerOffersList.deleteButton")}
          </Button>
          <Button onClick={seeApplicationsButtonClick} className="ms-2 btn-light btn-outline-success text-dark">
              {t("employerOffersList.applicationButton")}
          </Button>
      </>
  );
};

export default EmployerButtons;