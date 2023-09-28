import { Container } from "react-bootstrap";
import { Student } from "../../model/user";
import { Department } from "../../model/department";
import StudentOffersList from "../../components/StudentOffersList";
import { useTranslation } from "react-i18next";
import { useEffect, useState } from "react";
import { getOffersByDepartment } from "../../services/offerService";
import { Offer } from "../../model/offer";

// Temp until login works

const tempDepartment: Department = {
    id: 1,
    name: "",
    code: "",
}

export const tempStudent: Student = {
    id: 1,
    email: "",
    password: "",
    studentNumber: "1",
    department: tempDepartment,
}

interface Props {
    student: Student;
}

const StudentOfferView = ({student}: Props) => {
    const [offers, setOffers] = useState<Offer[]>([]);
    const [error, setError] = useState<string>("");
    const {t} = useTranslation();

    useEffect(() => {
        getOffersByDepartment(student.department!.id!)
        .then((res) => {
            setOffers(res.data);
        })
        .catch((err) => {
            console.log(err)
            if (err.request.status === 404) setError(t("studentOffersList.errors.departmentNotFound"));
        })
    }, [student.department, t]);

    return (
        <Container>
            <h1>{t("studentOffersList.viewTitle")}</h1>
            <StudentOffersList offers={offers} error={error}/>
        </Container>
    );
};

export default StudentOfferView;