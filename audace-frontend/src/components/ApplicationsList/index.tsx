import Application, {ApplicationStatus} from "../../model/application";
import ApplicationRow from "./ApplicationRow";
import { useTranslation } from "react-i18next";
import GenericTable from "../GenericTable";
import {UserType} from "../../model/user";

interface Props {
  applications: Application[];
  error: string;
  userType: UserType;
  updateApplicationsState?: (application: Application, applicationStatus: ApplicationStatus) => void;
}

const ApplicationsList = ({ applications, error, userType, updateApplicationsState}: Props) => {
  const { t } = useTranslation();

  return (
    <>
      <GenericTable list={applications} error={error} emptyListMessage="applicationsList.noApplications" title="applicationsList.title">
        <thead>
          <tr>
            {userType != UserType.Employer && <th>{t("applicationsList.offerTitle")}</th>}
            {userType != UserType.Student && <th>{t("applicationsList.studentName")}</th>}
            <th>{t("applicationsList.cv")}</th>
            {userType != UserType.Employer && <th>{t("applicationsList.organization")}</th>}
            <th>{t("applicationsList.status")}</th>
          </tr>
        </thead>
        <tbody>
          {applications.map((application) => (
            <ApplicationRow key={application.id} application={application} userType={userType} updateApplicationsState={updateApplicationsState} />
          ))}
        </tbody>
      </GenericTable>
    </>
  );
};

export default ApplicationsList;
