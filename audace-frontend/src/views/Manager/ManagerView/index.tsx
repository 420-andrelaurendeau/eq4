import { Container } from "react-bootstrap";
import { useEffect, useState } from "react";
import {
  getAcceptedApplicationsByDepartment,
  getDepartmentByManager,
} from "../../../services/managerService";
import ManagerApplicationsList from "../../../components/ManagerApplicationsList";
import Application from "../../../model/application";
import { getUserId } from "../../../services/authService";
import { getContractsByDepartmentId } from "../../../services/applicationService";

const ManagerView = () => {
  const [applications, setApplications] = useState<Application[]>([]);

  useEffect(() => {
    const managerId = getUserId();

    if (!managerId) return;

    const fetchData = async () => {
      try {
        const departmentRes = await getDepartmentByManager(parseInt(managerId));

        const applicationsRes = await getAcceptedApplicationsByDepartment(
          parseInt(managerId),
          departmentRes.data.id!
        );

        const contractRes = await getContractsByDepartmentId(
          departmentRes.data.id!
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
    <Container>
      <h1>Manager view</h1>
      <ManagerApplicationsList applications={applications} />
    </Container>
  );
};

export default ManagerView;
