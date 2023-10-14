import React from "react";
import {useNavigate, useParams} from "react-router-dom";
import {useTranslation} from "react-i18next";
import {Button, Container} from "react-bootstrap";

const EmployerHomePage = () => {
    const {userId} = useParams();
    const navigate = useNavigate();
    const {t} = useTranslation();

    const seeOffers = () => {
        navigate(`/employer/${userId}/offers`);
    }

    return (
        <Container>
            <h1>Employer {userId}</h1>
            <Button onClick={seeOffers}>{t("employer.seeOffersButton")}</Button>
        </Container>
    );
};

export default EmployerHomePage;