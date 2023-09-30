import { Container } from "react-bootstrap";
import { Student, UserType } from "../../model/user";
import { Department } from "../../model/department";
import { useTranslation } from "react-i18next";
import { useEffect, useState } from "react";
import { Offer } from "../../model/offer";
import OffersList from "../../components/OffersList";
import { getStudentOffersByDepartment } from "../../services/offerService";

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
        getStudentOffersByDepartment(student.department!.id!)
        .then((res) => {
            setOffers(res.data);
        })
        .catch((err) => {
            console.log(err)
            if (err.request.status === 404) setError(t("offersList.errors.departmentNotFound"));
        })
    }, [student.department, t]);

    return (
        <Container>
            <h1>{t("studentOffersList.viewTitle")}</h1>
            <OffersList offers={offers} error={error} userType={UserType.Student}/>
        </Container>
    );
};

export default StudentOfferView;