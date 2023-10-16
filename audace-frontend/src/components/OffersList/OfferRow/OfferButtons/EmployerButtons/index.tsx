import { Button } from "react-bootstrap";
import { useTranslation } from "react-i18next";
import { useState } from "react";
import { Offer } from "../../../../../model/offer";
import { Route } from "react-router-dom";

interface Props {
    disabled?: boolean;
    offer?: Offer;  
}

const EmployerButtons = ({ disabled, offer }: Props) => {
    const { t } = useTranslation();
    const [isDeleting, setIsDeleting] = useState(false);

    const editButtonClick = (event: React.MouseEvent<HTMLElement>) => {
        event.stopPropagation();
        console.log("Edit button clicked");
    };

    const deleteButtonClick = async (event: React.MouseEvent<HTMLElement>) => {
        event.stopPropagation();
        console.log("Delete button clicked");
        console.log("Offer:", offer);


        if (offer === undefined || offer.id === undefined) {
            console.error("No offerId provided");
            return;
        }

        setIsDeleting(true);

        try {
            const response = await fetch(`http://localhost:8080/employers/offers/${offer.id}`, {
                method: 'DELETE',
            });

            if (!response.ok) {
                throw new Error(`Failed to delete offer. Status: ${response.status}`);
            }

            console.log("Offer deleted successfully");
        } catch (error) {
            console.error("Failed to delete offer:", error);
        } finally {
            setIsDeleting(false);
        }
    };

    return (
        <>
            <Button disabled={disabled} onClick={editButtonClick} className="btn-warning me-2">
                {t("employerOffersList.editButton")}
            </Button>
            <Button
                disabled={disabled || isDeleting} 
                onClick={deleteButtonClick} 
                className="btn-danger"
            >
                {isDeleting ? t("employerOffersList.deletingButton") : t("employerOffersList.deleteButton")}
            </Button> 
        </> 
    ); 
}; 
 
export default EmployerButtons; 
 