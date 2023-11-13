import {Button} from "react-bootstrap";
import {useTranslation} from "react-i18next";
import {Application, ApplicationStatus} from "../../../../../model/application";
import {employerAcceptApplication, employerRefuseApplication} from "../../../../../services/applicationService";
import {getUserId} from "../../../../../services/authService";

interface Props {
    application: Application;
    updateApplicationsState?: (application: Application, applicationStatus: ApplicationStatus) => void;
}

const EmployerButtons = ({application, updateApplicationsState}: Props) => {
    const {t} = useTranslation();

    const acceptButtonClick = (event: React.MouseEvent<HTMLElement>) => {
        event.stopPropagation();
        employerAcceptApplication(parseInt(getUserId()!), application.id!).then((_) => {
            updateApplicationsState!(application, ApplicationStatus.ACCEPTED);
        });

    };

    const refuseButtonClick = (event: React.MouseEvent<HTMLElement>) => {
        event.stopPropagation();
        employerRefuseApplication(parseInt(getUserId()!), application.id!).then((_) => {
            updateApplicationsState!(application, ApplicationStatus.REFUSED);
        });

    };

    return (
        <>
            {application.applicationStatus === "PENDING" ? (<>
                    <Button onClick={acceptButtonClick} className="btn-light btn-outline-success ms-2">{t("employerApplicationsList.acceptButton")}</Button>
                    <Button onClick={refuseButtonClick} className="btn-light btn-outline-danger ms-2">{t("employerApplicationsList.refuseButton")}</Button>
                </>)
                : (<p>{t("employerApplicationsList." + application.applicationStatus)}</p>)
            }
        </>
    );
};
export default EmployerButtons;
