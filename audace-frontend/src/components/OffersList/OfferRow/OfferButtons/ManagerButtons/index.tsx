import { Button } from "react-bootstrap";
import { useTranslation } from "react-i18next";
import { acceptOffer, refuseOffer } from "../../../../../services/offerService";
import { Offer, OfferStatus } from "../../../../../model/offer";
import React from "react";

interface Props {
    disabled? : boolean;
    offer : Offer;
    updateOffersState?: (offer : Offer, offerStatus : OfferStatus) => void;
}

const ManagerButtons = ({disabled, offer, updateOffersState}: Props) => {
    const {t} = useTranslation();

    const acceptButtonClick = (event : React.MouseEvent<HTMLElement>) => {
        event.stopPropagation();
        acceptOffer(offer.id!)
            .then((_) => {
                updateOffersState!(offer, OfferStatus.ACCEPTED);
            }
            );
    }

    const refuseButtonClick = (event : React.MouseEvent<HTMLElement>) => {
        event.stopPropagation();
        refuseOffer(offer.id!)
            .then((_) => {
                updateOffersState!(offer, OfferStatus.REFUSED);
            }
        )
    }

    return (
        <>
            {offer.status === "PENDING" ?
            (<><Button disabled={disabled} onClick={acceptButtonClick} className="btn-success me-2">{t("managerOffersList.acceptButton")}</Button>
            <Button disabled={disabled} onClick={refuseButtonClick} className="btn-danger">{t("managerOffersList.refuseButton")}</Button></>) : <p>{t("managerOffersList." + offer.status)}</p>}
        </>
    );
};

export default ManagerButtons;