import {Button, Container} from "react-bootstrap";
import {useNavigate} from "react-router-dom";
import {useTranslation} from "react-i18next";

const EmployerView = () => {
    const {t} = useTranslation();
    const navigate = useNavigate();

    const seeOffers = () => {
        navigate(`/employer/offers`);
    };

    return (
        <Container>
            <h1 className="my-3">Employer view</h1>
            <Button onClick={seeOffers}>{t("employer.seeOffersButton")}</Button>
        </Container>
    )
}

export default EmployerView;