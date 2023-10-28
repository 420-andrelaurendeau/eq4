import Application from "../../model/application";
import ApplicationRow from "./ApplicationRow";
import { useTranslation } from "react-i18next";
import GenericTable from "../GenericTable";

interface Props {
  applications: Application[];
  error: string;
}

const ApplicationsList = ({ applications, error }: Props) => {
  const { t } = useTranslation();

  return (
    <>
      <GenericTable
        list={applications}
        error={error}
        emptyListMessage="applicationsList.noApplications"
        title="applicationsList.title"
      >
        <thead>
          <tr>
            <th>{t("applicationsList.offerTitle")}</th>
            <th>{t("applicationsList.organization")}</th>
            <th>{t("applicationsList.status")}</th>
          </tr>
        </thead>
        <tbody>
          {applications.map((application) => (
            <ApplicationRow key={application.id} application={application} />
          ))}
        </tbody>
      </GenericTable>
    </>
  );
};

export default ApplicationsList;
