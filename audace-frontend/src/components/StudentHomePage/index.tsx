import { Button } from "react-bootstrap";
import { useTranslation } from "react-i18next";
import { useNavigate, useParams } from "react-router-dom";

const StudentHomePage = () => {
    const {userId} = useParams();
    const navigate = useNavigate();
    const {t} = useTranslation();

    const seeOffers = () => {
        navigate(`/student/${userId}/offers`);
    }

    return (
        <div>
            <h1>Student {userId}</h1>
            <Button onClick={seeOffers}>{t("student.seeOffersButton")}</Button>
        </div>
    );
};

export default StudentHomePage;