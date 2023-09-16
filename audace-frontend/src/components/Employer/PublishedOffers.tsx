import React, {useEffect, useState} from 'react';
import DataService from "../../services/DataService";
import {Link, useParams} from "react-router-dom";
import EmployerNavBar from "./EmployerNavBar";
import {Container, Table} from "reactstrap";
import { Offer } from "../../model/offer"


function PublishedOffers(){
    const [offers, setOffers] = useState<Offer[]>([]);
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
                <td>{offer.internshipStartDate.toString()}</td>
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
export default PublishedOffers;