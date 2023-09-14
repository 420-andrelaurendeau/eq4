import React, {useEffect, useState} from 'react';
import DataService from "../../services/DataService";
import {useParams} from "react-router-dom";
import EmployerNavBar from "./EmployerNavBar";
import {Container, Table} from "reactstrap";
function PublishedOffers(){
    const [offers, setOffers] = useState([]);
    const params = useParams();

    useEffect(() => {
        DataService.getOffersByEmployerId(params.id)
            .then(response => {
                setOffers(response.data)
            });
    });
    const publishedOffers = offers.map(
        offer => {
            return <tr key={offer.id}>
                <td>{offer.title}</td>
                <td>{offer.description}</td>
                <td>{offer.internshipStartDate}</td>
                <td>{offer.internshipEndDate}</td>
                <td>{offer.offerEndDate}</td>
                <td>{offer.department}</td>
            </tr>
        }
    );


    return <div>
        <EmployerNavBar/>
        <Container className="mt-2">
            <h3>Mes offres publies</h3>
            <Table className="m-4">

            </Table>
        </Container>
    </div>
}