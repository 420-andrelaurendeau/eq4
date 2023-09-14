import { Container } from "react-bootstrap";
import { Student } from "../../model/user";
import { Department } from "../../model/department";
import StudentOffersList from "../../components/StudentOffersList";
import { useTranslation } from "react-i18next";

const tempDepartment: Department = {
    id: 1,
    name: "",
    code: "",
}

const tempStudent: Student = {
    id: 1,
    email: "",
    password: "",
    studentNumber: "1",
    department: tempDepartment,
}

const StudentOfferView = () => {
    const {t} = useTranslation();

    return (
        <Container>
            <h1>{t("studentOffersList.viewTitle")}</h1>
            <StudentOffersList student={tempStudent}/>
        </Container>
    );
};

export default StudentOfferView;