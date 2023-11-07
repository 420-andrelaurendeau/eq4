import { Alert, Container } from "react-bootstrap";
import { useEffect, useState } from "react";
import {
  getAcceptedApplicationsByDepartment,
  getDepartmentByManager
} from "../../../services/managerService";
import ManagerApplicationsList from "../../../components/ManagerApplicationsList";
import Application from "../../../model/application";
import { getUserId } from "../../../services/authService";
import { getContractsByDepartmentId } from "../../../services/applicationService";
import { useTranslation } from "react-i18next";
import { Department } from "../../../model/department";
import ManagerStudentByInternshipStatusList from "../../../components/ManagerStudentByInternshipStatusList";

interface Props {
  isContractCreated?: boolean;
}

const ManagerView = ({ isContractCreated }: Props) => {
  const [applications, setApplications] = useState<Application[]>([]);
  const [department] = useState<Department>();
  const { t } = useTranslation();

  useEffect(() => {
    const managerId = getUserId();

    if (!managerId) return;

    const fetchData = async () => {
      try {
        const department = await getDepartmentByManager(parseInt(managerId));

        const applicationsRes = await getAcceptedApplicationsByDepartment(
          parseInt(managerId),
          department.data.id!
        );

        const contractRes = await getContractsByDepartmentId(
          department.data.id!
        );

        const applications = applicationsRes.data;
        const contracts = contractRes.data;

        const filteredApplications = applications.filter((application) => {
          return !contracts.some((contract) => {
            return contract.application.id === application.id;
          });
        });
        setApplications(filteredApplications);
      } catch (err: any) {
        console.log(
          "Accepted applications fetching error: " + err.response.data
        );
      }
    };

    fetchData();
  }, []);

  return (
    <Container className="mt-3">
      <Alert variant="success" hidden={isContractCreated === undefined}>
        {t("manager.contractCreated")}
      </Alert>
      <h1>{t("manager.title")}</h1>
      <ManagerApplicationsList applications={applications} />
      <ManagerStudentByInternshipStatusList />
    </Container>
  );
};

export default ManagerView;
