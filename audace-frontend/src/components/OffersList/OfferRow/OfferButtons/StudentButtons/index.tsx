import { Button } from "react-bootstrap";
import { useTranslation } from "react-i18next";

interface Props {
    disabled?: boolean;
}

const StudentButtons = ({disabled}: Props) => {
    const {t} = useTranslation();

    const applyButtonClick = (event : React.MouseEvent<HTMLElement>) => {
        event.stopPropagation();
    }

    return (
        <>
            <Button disabled={disabled} onClick={applyButtonClick}>{t("studentOffersList.applyButton")}</Button>
        </>
    );
};

export default StudentButtons;