import { Container } from "react-bootstrap";
import { Student, UserType } from "../../model/user";
import { useTranslation } from "react-i18next";
import { useEffect, useState } from "react";
import { Offer } from "../../model/offer";
import OffersList from "../../components/OffersList";
import { getStudentOffersByDepartment } from "../../services/offerService";
import { useParams } from "react-router-dom";
import { getStudentById } from "../../services/userService";

const StudentOfferView = () => {
    const [student, setStudent] = useState<Student>();
    const {id} = useParams();
    const [offers, setOffers] = useState<Offer[]>([]);
    const [error, setError] = useState<string>("");
    const {t} = useTranslation();

    useEffect(() => {
        getStudentById(parseInt(id!))
            .then((res) => {
                setStudent(res.data);
            })
            .catch((err) => {
                console.log(err)
                if (err.request.status === 404) setError(t("studentOffersList.errors.studentNotFound"));
            })
    }, [student, id, t]);

    useEffect(() => {
        if (student === undefined) return;

        getStudentOffersByDepartment(student.department!.id!)
        .then((res) => {
            setOffers(res.data);
        })
        .catch((err) => {
            console.log(err)
            if (err.request.status === 404) setError(t("offersList.errors.departmentNotFound"));
        })
    }, [student, t]);

    return (
        <Container>
            <h1>{t("studentOffersList.viewTitle")}</h1>
            <OffersList offers={offers} error={error} userType={UserType.Student}/>
        </Container>
    );
};

export default StudentOfferView;