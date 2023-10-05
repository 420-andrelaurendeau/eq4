import { Button } from "react-bootstrap";
import { useTranslation } from "react-i18next";

interface Props {
    disabled?: boolean;
}

const EmployerButtons = ({disabled}: Props) => {
    const {t} = useTranslation();

    const editButtonClick = (event : React.MouseEvent<HTMLElement>) => {
        event.stopPropagation();
    }

    const deleteButtonClick = (event : React.MouseEvent<HTMLElement>) => {
        event.stopPropagation();
    }

    return (
        <>
            <Button disabled={disabled} onClick={editButtonClick} className="btn-warning me-2">{t("employerOffersList.editButton")}</Button>
            <Button disabled={disabled} onClick={deleteButtonClick} className="btn-danger">{t("employerOffersList.deleteButton")}</Button>
        </>
    );
};

export default EmployerButtons;