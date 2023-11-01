import { Button, Container } from "react-bootstrap";
import { useTranslation } from "react-i18next";
import { useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import {
    getAcceptedApplicationsByDepartment,
    getDepartmentByManager,
    getStudentByApplication,
} from "../../../services/managerService";
import ManagerApplicationsList from "../../../components/ManagerApplicationsList";
import Application from "../../../model/application";
import { getUserId } from "../../../services/authService";

const ManagerView = () => {
    const navigate = useNavigate();
    const { t } = useTranslation();
    const [applications, setApplications] = useState<Application[]>([]);
    const [, setError] = useState<string>("");
    const managerId = parseInt(getUserId()!);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const departmentRes = await getDepartmentByManager(managerId);
                setError("");

                const applicationsRes = await getAcceptedApplicationsByDepartment(departmentRes.data.id!);
                setApplications(applicationsRes.data);
                setError("");

                retrieveApplicationStudents();
            } catch (err: any) {
                setError(err.response.data);
                console.log("Accepted applications fetching error: " + err.response.data);
            }
        };

        fetchData().then(() => console.log("done"));
    }, [managerId]);

    const retrieveApplicationStudents = () => {
        applications.forEach(async (application) => {
            try {
                const res = await getStudentByApplication(application);
                application.cv!.student = res.data;
                setError("");
            } catch (err: any) {
                setError(err.response.data);
            }
        });
    };

    const seeOffers = () => {
        navigate(`/manager/offers`);
    };
    const seeCvs = () => {
        navigate(`/manager/cvs`);
    };

    return (
        <Container>
            <h1>Manager view</h1>
            <Button onClick={seeOffers}>{t("manager.seeOffersButton")}</Button>
            <Button onClick={seeCvs}>{t("manager.seeCvsButton")}</Button>
            <ManagerApplicationsList applications={applications} />
        </Container>
    );
};

export default ManagerView;
