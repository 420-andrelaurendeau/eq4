import { useEffect, useState } from "react";
import { Student } from "../../model/user";
import { Offer } from "../../model/offer";
import { getOffersByDepartment } from "../../services/offerService";

interface Props {
    student: Student;
}

const StudentOffers = ({student}: Props) => {
    const [offers, setOffers] = useState<Offer[]>([]);

    useEffect(() => {
        getOffersByDepartment(student.department.id!)
        .then((res) => {
            setOffers(res.data);
        })
        .catch((err) => {
            console.log(err);
        })
    }, [student.department]);

    return (
        <>
            <h1>Available offers</h1>
            {
                offers.length > 0 ?
                offers.map((offer) => {return <p>{offer.description}</p>}) :
                <p>No offers available</p>
            }
        </>
    )
}

export default StudentOffers;