import { Button } from "react-bootstrap";
import { useTranslation } from "react-i18next";

interface Props {
    disabled? : boolean;
}

const ManagerButtons = ({disabled}: Props) => {
    const {t} = useTranslation();

    const acceptButtonClick = (event : React.MouseEvent<HTMLElement>) => {
        event.stopPropagation();
    }

    const refuseButtonClick = (event : React.MouseEvent<HTMLElement>) => {
        event.stopPropagation();
    }

    return (
        <>
            <Button disabled={disabled} onClick={acceptButtonClick} className="btn-success me-2">{t("managerOffersList.acceptButton")}</Button>
            <Button disabled={disabled} onClick={refuseButtonClick} className="btn-danger">{t("managerOffersList.refuseButton")}</Button>
        </>
    );
};

export default ManagerButtons;