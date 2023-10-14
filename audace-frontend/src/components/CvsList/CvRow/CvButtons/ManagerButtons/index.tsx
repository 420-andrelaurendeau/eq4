import { Button } from "react-bootstrap";
import { useTranslation } from "react-i18next";
import { CV, CVStatus } from "../../../../../model/cv";
import { acceptCv, refuseCv } from "../../../../../services/cvService";

interface Props {
    disabled? : boolean;
    cv : CV;
    updateCvsState?: (cv : CV, cvStatus : CVStatus) => void;
}

const ManagerButtons = ({disabled, cv, updateCvsState}: Props) => {
    const {t} = useTranslation();

    const acceptButtonClick = (event : React.MouseEvent<HTMLElement>) => {
        event.stopPropagation();
        acceptCv(cv.id!)
            .then((_) => {
                updateCvsState!(cv, CVStatus.ACCEPTED);
            }
        );
    }

    const refuseButtonClick = (event : React.MouseEvent<HTMLElement>) => {
        event.stopPropagation();
        refuseCv(cv.id!)
            .then((_) => {
                updateCvsState!(cv, CVStatus.REFUSED);
            }
        );
    }

    return (
        <>
            {cv.cvStatus === CVStatus.PENDING ?
            (<><Button disabled={disabled} onClick={acceptButtonClick} className="btn-success me-2">{t("managerOffersList.acceptButton")}</Button>
            <Button disabled={disabled} onClick={refuseButtonClick} className="btn-danger">{t("managerOffersList.refuseButton")}</Button></>) : <p>{t("managerOffersList." + cv.cvStatus)}</p>}
        </>
    );
};

export default ManagerButtons;