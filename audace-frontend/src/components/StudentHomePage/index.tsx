import { Button } from "react-bootstrap";
import { useTranslation } from "react-i18next";
import { useParams } from "react-router-dom";

const StudentHomePage = () => {
    const {userId} = useParams();
    const {t} = useTranslation();

    return (
        <div>
            <h1>Student {userId}</h1>
            <Button href={`/student/${userId}/offers`}>{t("student.seeOffersButton")}</Button>
        </div>
    );
};

export default StudentHomePage;