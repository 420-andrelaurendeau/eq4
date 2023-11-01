import { Button } from "react-bootstrap";
import { useTranslation } from "react-i18next";
import { CV, CVStatus } from "../../../../../model/cv";
import { acceptCv, refuseCv } from "../../../../../services/cvService";
import { getUserId } from "../../../../../services/authService";

interface Props {
  disabled?: boolean;
  cv: CV;
  updateCvsState?: (cv: CV, cvStatus: CVStatus) => void;
}

const ManagerButtons = ({ disabled, cv, updateCvsState }: Props) => {
  const { t } = useTranslation();

  const acceptButtonClick = (event: React.MouseEvent<HTMLElement>) => {
    event.stopPropagation();
    acceptCv(parseInt(getUserId()!), cv.id!).then((_) => {
      updateCvsState!(cv, CVStatus.ACCEPTED);
    });
  };

  const refuseButtonClick = (event: React.MouseEvent<HTMLElement>) => {
    event.stopPropagation();
    refuseCv(parseInt(getUserId()!), cv.id!).then((_) => {
      updateCvsState!(cv, CVStatus.REFUSED);
    });
  };

  return (
    <>
      {cv.cvStatus === CVStatus.PENDING ? (
        <>
          <Button
            disabled={disabled}
            onClick={acceptButtonClick}
            variant="outline-success"
            className="text-dark me-2"
          >
            {t("managerCvsList.acceptButton")}
          </Button>
          <Button
            disabled={disabled}
            onClick={refuseButtonClick}
            variant="outline-danger"
            className="text-dark"
          >
            {t("managerCvsList.refuseButton")}
          </Button>
        </>
      ) : (
        <p>{t("managerCvsList." + cv.cvStatus)}</p>
      )}
    </>
  );
};

export default ManagerButtons;
