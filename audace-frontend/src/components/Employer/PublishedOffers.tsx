import React, {useEffect, useState} from 'react';

import {Link, useParams} from "react-router-dom";
//import EmployerNavBar from "./EmployerNavBar";
//import {Container, Table} from "reactstrap";
import { Offer } from "../../model/offer"
import {getAllOffersByEmployerId} from "../../services/offerService";


/*function PublishedOffers(){
    const [offers, setOffers] = useState<Offer[]>([]);
    const params = useParams();

    useEffect(() => {
        getAllOffersByEmployerId(params.id)
            .then(response => {
                setOffers(response.data)
            });
    });
    const publishedOffers = offers.map(
        offer => {
            return <tr key={offer.id}>
                <td>{offer.title}</td>
                <td>{new Date(offer.internshipStartDate).toLocaleString()}</td>
                <td>{offer.department.name}</td>
            </tr>
        }
    );


    return <div>
        <EmployerNavBar/>
        <Container className="mt-2">
            <h3>Mes offres publies</h3>
            <Table className="m-4">
                <thead>
                    <tr>
                        <td>Titre du poste</td>
                        <td>Date de début</td>
                        <td>Secteur d'activité</td>
                    </tr>
                </thead>
                <tbody>
                    {publishedOffers}
                </tbody>
            </Table>
        </Container>
    </div>
}
export default PublishedOffers;*/