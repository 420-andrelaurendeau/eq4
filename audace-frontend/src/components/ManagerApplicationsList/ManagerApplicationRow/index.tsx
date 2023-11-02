import { Button } from "react-bootstrap";
import Application from "../../../model/application";
import { useTranslation } from "react-i18next";
import { useNavigate } from "react-router-dom";

interface Props {
  application: Application;
}
const ManagerApplicationRow = ({ application }: Props) => {
  const navigate = useNavigate();
  const { t } = useTranslation();

  return (
    <tr>
      <td>{application.offer!.title}</td>
      <td>{application.offer!.employer.organisation}</td>
      <td>
        {application.cv?.student.firstName} {application.cv?.student.lastName}
      </td>
      <td>
        <Button
          variant="outline-secondary"
          className="text-dark"
          onClick={() => {
            navigate(`/manager/contracts/new/${application.id}`)
          }
          }
        >
          {t("manager.createContractButton")}
        </Button>
      </td>
    </tr>
  );
};

export default ManagerApplicationRow;