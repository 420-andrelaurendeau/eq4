import { useEffect, useState } from "react";
import { Student } from "../../model/user";
import { Offer } from "../../model/offer";
import { getOffersByDepartment } from "../../services/offerService";
import StudentOffer from "./StudentOffer";
import { Table } from "react-bootstrap";

interface Props {
    student: Student;
}

const StudentOffersList = ({student}: Props) => {
    const [offers, setOffers] = useState<Offer[]>([]);
    const [error, setError] = useState<string>("");

    useEffect(() => {
        getOffersByDepartment(student.department.id!)
        .then((res) => {
            setOffers(res.data);
        })
        .catch((err) => {
            if (err.response.status === 404) setError("Department Not Found");
        })
    }, [student.department]);

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
                                <th>Title</th>
                                <th>Start Date</th>
                                <th>End Date</th>
                            </tr>
                        </thead>
                        <tbody>
                            {offers.map((offer) => {return <StudentOffer key={offer.id} offer={offer} />})}
                        </tbody>
                    </Table>
                    :
                    <p>No offers available</p>
            }
        </>
    )
}

export default StudentOffersList;