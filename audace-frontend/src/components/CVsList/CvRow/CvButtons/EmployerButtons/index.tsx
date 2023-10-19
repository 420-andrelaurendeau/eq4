import { Button } from "react-bootstrap";
import { useTranslation } from "react-i18next";
import { CV, CVStatus } from "../../../../../model/cv";

interface Props {
    disabled? : boolean;
    cv : CV;
    updateCvsState?: (cv : CV, cvStatus : CVStatus) => void;
}

const EmployerButtons = ({disabled, cv, updateCvsState}: Props) => {
    const {t} = useTranslation();

    const acceptButtonClick = (event : React.MouseEvent<HTMLElement>) => {
    }

    const refuseButtonClick = (event : React.MouseEvent<HTMLElement>) => {
    }

    return (
        <>
            {cv.cvStatus === CVStatus.PENDING ?
            (<><Button disabled={disabled} onClick={acceptButtonClick} className="btn-success me-2">{t("employerCvsList.acceptButton")}</Button>
            <Button disabled={disabled} onClick={refuseButtonClick} className="btn-danger">{t("employerCvsList.refuseButton")}</Button></>) : <p>{t("employerCvsList." + cv.cvStatus)}</p>}
        </>
    );
};

export default EmployerButtons;