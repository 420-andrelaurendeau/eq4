import { Button } from "react-bootstrap";
import { useTranslation } from "react-i18next";
import { useState } from "react";
import { Offer } from "../../../../../model/offer";
import { useNavigate } from 'react-router-dom';
import http from "../../../../../constants/http";

interface Props {
    disabled?: boolean;
    offer?: Offer;  
    hideRow?: () => void;
}

const EmployerButtons = ({ disabled, offer, hideRow }: Props) => {
    const { t } = useTranslation();
    const [isDeleting, setIsDeleting] = useState(false);
    const navigate = useNavigate();

    const editButtonClick = (event: React.MouseEvent<HTMLElement>) => {
        event.stopPropagation();
        console.log("Edit button clicked");
        console.log("Offer:", offer);

        if (offer?.id) {
            navigate(`/employer/editoffer/${offer.id}`); 
        } else {
            console.error("No offerId provided");
        }
    };

    const deleteButtonClick = async (event: React.MouseEvent<HTMLElement>) => {
        event.stopPropagation();
        console.log("Delete button clicked");
        console.log("Offer:", offer);
    
        if (offer === undefined || offer.id === undefined) {
            console.error("No offerId provided");
            return;
        }
    
        hideRow!();
        setIsDeleting(true);
    
        try {
            const response = await http.delete(`/employers/offers/${offer.id}`);
    
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
 