import {useEffect, useState} from "react";
import {Offer} from "../../../model/offer";
import {useTranslation} from "react-i18next";
import {useParams} from "react-router-dom";
import {getAllOffersByEmployerId} from "../../../services/offerService";
import {Table} from "react-bootstrap";
import StudentOffer from "../../StudentOffersList/StudentOffer";
import EmployerOffer from "./EmployerOffer";

function EmployerOffersList(){
    const [offers, setOffers] = useState<Offer[]>([]);

    const [error, setError] = useState<string>("");
    const {t} = useTranslation();
    const params = useParams();

    useEffect(() => {
        getAllOffersByEmployerId(params.id)
            .then((res) => {
                setOffers(res.data);
            })
            .catch((err) => {
                console.log(err)
            });
    });
    return (
        <>
            {error !== "" ?
                <p>{error}</p>
                :
                offers.length > 0 ?
                    <Table striped bordered hover size="sm">
                        <thead>
                            <tr>
                                <th>{t("employerOffersList.title")}</th>
                                <th>{t("employerOffersList.internshipStartDate")}</th>
                                <th>{t("employerOffersList.internshipEndDate")}</th>
                            </tr>
                        </thead>
                        <tbody>
                            {offers.map((offer) => {return <EmployerOffer key={offer.id} offer={offer} />})}
                        </tbody>
                    </Table>
                    :
                    <p>{t("employerOffersList.noOffers")}</p>
            }
        </>
    )
}
export default EmployerOffersList;