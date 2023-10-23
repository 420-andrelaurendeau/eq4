import { useTranslation } from "react-i18next";
import Application from "../../../model/application";

interface Props {
  application: Application;
}

const ApplicationRow = ({ application }: Props) => {
  const { t } = useTranslation();

  return (
    <tr>
      <td>{application.offer!.title}</td>
      <td>{application.offer!.employer.organisation}</td>
      <td>
        {t(`applicationsList.row.status.${application.applicationStatus}`)}
      </td>
    </tr>
  );
};

export default ApplicationRow;
