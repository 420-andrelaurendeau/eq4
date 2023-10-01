import { Button } from "react-bootstrap";
import { useTranslation } from "react-i18next";
import { useParams } from "react-router-dom";

const ManagerHomePage = () => {
    const {userId} = useParams();
    const {t} = useTranslation();

    return (
        <div>
            <h1>Manager {userId}</h1>
            <Button href={`/manager/${userId}/offers`}>{t("manager.seeOffersButton")}</Button>
        </div>
    );
};

export default ManagerHomePage;