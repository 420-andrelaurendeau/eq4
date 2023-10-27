import { Button } from "react-bootstrap";
import { useTranslation } from "react-i18next";
import { Offer } from "../../../../../model/offer";

interface Props {
    disabled?: boolean;
    seeApplications?: (offer : Offer) => void;
    offer : Offer;
}

const EmployerButtons = ({disabled, seeApplications, offer}: Props) => {
    const {t} = useTranslation();

    const editButtonClick = (event : React.MouseEvent<HTMLElement>) => {
        event.stopPropagation();
    }

    const deleteButtonClick = (event : React.MouseEvent<HTMLElement>) => {
        event.stopPropagation();
    }
    const seeApplicationsButtonClick = (event : React.MouseEvent<HTMLElement>) => {
        event.stopPropagation();
        seeApplications!(offer);
    }

    return (
        <>
            <Button disabled={disabled} onClick={editButtonClick} className="btn-light btn-outline-warning text-dark">{t("employerOffersList.editButton")}</Button>
            <Button disabled={disabled} onClick={deleteButtonClick} className="btn-light btn-outline-danger text-dark ms-2">{t("employerOffersList.deleteButton")}</Button>
            {seeApplications !== undefined ? <Button disabled={disabled} onClick={seeApplicationsButtonClick} className="ms-2 btn-light btn-outline-success text-dark">{t("employerOffersList.applicationButton")}</Button> : null}
        </>
    );
};

export default EmployerButtons;