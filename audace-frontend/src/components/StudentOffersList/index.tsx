import { useEffect, useState } from "react";
import { Student } from "../../model/user";
import { Offer } from "../../model/offer";
import { getOffersByDepartment } from "../../services/offerService";
import StudentOffer from "./StudentOffer";
import { Table } from "react-bootstrap";
import { useTranslation } from "react-i18next";

interface Props {
    student: Student;
}

const StudentOffersList = ({student}: Props) => {
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
        <>
            {
                error !== "" 
                ? 
                <p>{error}</p> 
                : 
                offers.length > 0
                    ?
                    <Table striped bordered hover size="sm">
                        <thead>
                            <tr>
                                <th>{t("studentOffersList.title")}</th>
                                <th>{t("studentOffersList.internshipStartDate")}</th>
                                <th>{t("studentOffersList.internshipEndDate")}</th>
                            </tr>
                        </thead>
                        <tbody>
                            {offers.map((offer) => {return <StudentOffer key={offer.id} offer={offer} />})}
                        </tbody>
                    </Table>
                    :
                    <p>{t("studentOffersList.noOffers")}</p>
            }
        </>
    )
}

export default StudentOffersList;