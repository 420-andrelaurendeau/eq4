import { Button } from "react-bootstrap";
import { useTranslation } from "react-i18next";
import { useNavigate, useParams } from "react-router-dom";

const ManagerHomePage = () => {
    const {userId} = useParams();
    const navigate = useNavigate();
    const {t} = useTranslation();

    const seeOffers = () => {
        navigate(`/manager/${userId}/offers`);
    }

    return (
        <div>
            <h1>Manager {userId}</h1>
            <Button onClick={seeOffers}>{t("manager.seeOffersButton")}</Button>
        </div>
    );
};

export default ManagerHomePage;