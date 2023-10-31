import { Button, Container } from "react-bootstrap";
import { useTranslation } from "react-i18next";
import { useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import {
    getAcceptedApplicationsByDepartment,
    getDepartmentByManager,
    getStudentByApplication,
} from "../../../services/managerService";
import { Department } from "../../../model/department";
import { Employer } from "../../../model/user";
import { CVStatus } from "../../../model/cv";
import ManagerApplicationsList from "../../../components/ManagerApplicationsList";
import Application, { ApplicationStatus } from "../../../model/application";
import { Offer, OfferStatus } from "../../../model/offer";
import { getUserId } from "../../../services/authService";

const ManagerView = () => {
    const navigate = useNavigate();
    const { t } = useTranslation();
    const [applications, setApplications] = useState<Application[]>([]);
    const [, setError] = useState<string>("");
    const [department, setDepartment] = useState<Department | undefined>(undefined); // Initialize as undefined
    const managerId = parseInt(getUserId()!);
    const [tempApplications] = useState<Application[]>([]);

    const tempTest = () => {
        if (applications.length === 3) {
            return;
        }
        const tempEmployer: Employer = {
            id: 2,
            firstName: "Chad",
            lastName: "Israd",
            email: "ChadIsRad@myspace.com",
            phone: "123456789",
            address: "1234 Street",
            password: "password",
            type: "employer",
            organisation: "Chad's Smoke Store",
            position: "Chad",
            extension: "1234"
        };

        const tempStudent = {
            id: 1,
            firstName: "Chad",
            lastName: "Is Rad",
            email: "ChadIsRad@myspace.com",
            phone: "123456789",
            address: "1234 Street",
            password: "password",
            type: "student",
            studentNumber: "123456788",
            department: department!
        };

        const tempCV = {
            id: 1,
            fileName: "ChadIsRadCV",
            content: "I am Chad and I am rad",
            student: tempStudent,
            cvStatus: CVStatus.ACCEPTED
        };

        const tempOffer: Offer = {
            id: 1,
            title: "Vape tester at the raddest vape shop in m-town",
            description: "required to vape and be rad",
            internshipStartDate: new Date(),
            internshipEndDate: new Date(),
            offerEndDate: new Date(),
            availablePlaces: 1,
            employer: tempEmployer,
            department: department!,
            offerStatus: OfferStatus.ACCEPTED
        };

        const tempApplication: Application = {
            id: 1,
            student: tempStudent,
            offer: tempOffer,
            cv: tempCV,
            applicationStatus: ApplicationStatus.ACCEPTED
        };

        tempApplications.push(tempApplication);
    };

    useEffect(() => {
        getDepartmentByManager(managerId)
            .then((res) => {
                setDepartment(res.data);
                setError("");

                getAcceptedApplicationsByDepartment(department!.id!)
                    .then((applicationsRes) => {
                        setApplications(applicationsRes.data);
                        setError("");
                        retrieveApplicationStudents();
                        tempTest();
                    })
                    .catch((err) => {
                        setError(err.response.data);
                    });
            })
            .catch((err) => {
                setError(err);
            });
        retrieveApplicationStudents();
        tempTest();
        }, [managerId]);

    const retrieveApplicationStudents = () => {
        applications.forEach((application) => {
            getStudentByApplication(application)
                .then((res) => {
                    application.student = res.data;
                    setError("");
                })
                .catch((err) => {
                    setError(err.response.data);
                });
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
            <ManagerApplicationsList applications={tempApplications} />
        </Container>
    );
};

export default ManagerView;
