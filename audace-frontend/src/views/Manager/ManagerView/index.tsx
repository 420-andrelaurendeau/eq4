import React, { useEffect, useState } from "react";
import { Alert, Container } from "react-bootstrap";
import {
  getAcceptedApplicationsByDepartment,
  getDepartmentByManager,
} from "../../../services/managerService";
import ManagerApplicationsList from "../../../components/ManagerApplicationsList";
import Application from "../../../model/application";
import { getUserId } from "../../../services/authService";
import { useTranslation } from "react-i18next";
import ManagerStudentByInternshipStatusList from "../../../components/ManagerStudentByInternshipStatusList";
import { getContractsByDepartmentId } from "../../../services/contractService";
import ManagerSignContract from "../../../components/ManagerSignContract";

interface Props {
  isContractCreated?: boolean;
}

const ManagerView = ({ isContractCreated }: Props) => {
  const [applications, setApplications] = useState<Application[]>([]);
  const [departmentId, setDepartmentId] = useState<number | null>(null);
  const { t } = useTranslation();

  useEffect(() => {
    const managerId = getUserId();

    if (!managerId) return;

    const fetchData = async () => {
      try {
        const departmentRes = await getDepartmentByManager(parseInt(managerId));
        if (departmentRes.data && departmentRes.data.id) {
          setDepartmentId(departmentRes.data.id);

          const applicationsRes = await getAcceptedApplicationsByDepartment(
            parseInt(managerId),
            departmentRes.data.id
          );
          const contractRes = await getContractsByDepartmentId(
            departmentRes.data.id
          );

          const applications = applicationsRes.data;
          const contracts = contractRes.data;

          const filteredApplications = applications.filter((application) => {
            return !contracts.some((contract) => {
              return contract.application.id === application.id;
            });
          });

          setApplications(filteredApplications);
        }
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
      <h1 className="my-3">{t("manager.title")}</h1>
      <ManagerApplicationsList applications={applications} />
      <ManagerStudentByInternshipStatusList />
      {departmentId && <ManagerSignContract departmentId={departmentId} />}
    </Container>
  );
};

export default ManagerView;
