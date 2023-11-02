import { Container } from "react-bootstrap";
import { useEffect, useState } from "react";
import {
  getAcceptedApplicationsByDepartment,
  getDepartmentByManager,
} from "../../../services/managerService";
import ManagerApplicationsList from "../../../components/ManagerApplicationsList";
import Application from "../../../model/application";
import { getUserId } from "../../../services/authService";

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
        setApplications(applicationsRes.data);
      } catch (err: any) {
        console.log(
          "Accepted applications fetching error: " + err.response.data
        );
      }
    };

    fetchData().then(() => { });
  }, []);

  return (
    <Container>
      <h1>Manager view</h1>
      <ManagerApplicationsList applications={applications} />
    </Container>
  );
};

export default ManagerView;
