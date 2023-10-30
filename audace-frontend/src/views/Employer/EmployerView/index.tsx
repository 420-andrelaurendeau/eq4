import React, { useEffect, useState } from "react";
import { Container, Button } from "react-bootstrap";
import { useTranslation } from "react-i18next";
import { Employer, UserType } from "../../../model/user";
import { useNavigate } from "react-router";
import { getUserId } from "../../../services/authService";
import { getEmployerById } from "../../../services/userService";
import EmployerApplicationView from "../EmployerApplicationView";

const EmployerView = () => {
    const [employer, setEmployer] = useState<Employer>();
    const [error, setError] = useState<string>("");
    const { t } = useTranslation();
    const navigate = useNavigate();

    useEffect(() => {
        if (employer !== undefined) return;
        const id = getUserId();
        if (id == null) {
            navigate("/pageNotFound");
            return;
        }

        getEmployerById(parseInt(id!))
            .then((res) => {
                setEmployer(res.data);
            })
            .catch((err) => {
                console.log(err)
                if (err.request.status === 404) setError(t("employer.errors.employerNotFound"));
            })
    }, [employer, navigate, t]);

    const seeOffers = () => {
        navigate(`/employer/offers`);
    };
    const seeApplications = () => {
        navigate(`/employer/applications`);
    };



    return (
        <Container>
            <h1 className="my-3">{employer?.firstName} {employer?.lastName}</h1>
            <Button onClick={seeOffers}>{t("employer.seeOffersButton")}</Button>
            <Button className="mx-2" onClick={seeApplications}>{t("employer.seeApplicationsButton")}</Button>
            <EmployerApplicationView />
        </Container>
    )
}

export default EmployerView;