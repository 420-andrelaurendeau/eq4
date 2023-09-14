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

    useEffect(() => {
        getOffersByDepartment(student.department.id!)
        .then((res) => {
            console.log(res.data)
            setOffers(res.data);
        })
        .catch((err) => {
            console.log(err);
        })
    }, [student.department]);

    return (
        <>
            {
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