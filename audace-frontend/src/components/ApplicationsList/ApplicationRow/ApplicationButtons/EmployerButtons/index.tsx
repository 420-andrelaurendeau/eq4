import { Button } from "react-bootstrap";
import { useTranslation } from "react-i18next";
import {
  Application,
  ApplicationStatus,
} from "../../../../../model/application";
import {
  employerAcceptApplication,
  employerRefuseApplication,
} from "../../../../../services/applicationService";
import { getUserId } from "../../../../../services/authService";

interface Props {
  application: Application;
  updateApplicationsState?: (
    application: Application,
    applicationStatus: ApplicationStatus
  ) => void;
}

const EmployerButtons = ({ application, updateApplicationsState }: Props) => {
  const { t } = useTranslation();

  const acceptButtonClick = (event: React.MouseEvent<HTMLElement>) => {
    event.stopPropagation();
    employerAcceptApplication(parseInt(getUserId()!), application.id!).then(
      () => {
        updateApplicationsState!(application, ApplicationStatus.ACCEPTED);
      }
    );
  };

  const refuseButtonClick = (event: React.MouseEvent<HTMLElement>) => {
    event.stopPropagation();
    employerRefuseApplication(parseInt(getUserId()!), application.id!).then(
      () => {
        updateApplicationsState!(application, ApplicationStatus.REFUSED);
      }
    );
  };

  return (
    <>
      <Button onClick={acceptButtonClick} className="btn-success me-2">
        {t("employerApplicationsList.acceptButton")}
      </Button>
      <Button onClick={refuseButtonClick} className="btn-danger">
        {t("employerApplicationsList.refuseButton")}
      </Button>
    </>
  );
};
export default EmployerButtons;
