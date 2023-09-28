import { Offer } from "../../model/offer";
import { Table } from "react-bootstrap";
import { useTranslation } from "react-i18next";
import OfferRow from "./OfferRow";

interface Props {
    offers: Offer[];
    error: string;
}

const OffersList = ({offers, error}: Props) => {
    const {t} = useTranslation();

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
                                <th>{t("offersList.title")}</th>
                                <th>{t("offersList.internshipStartDate")}</th>
                                <th>{t("offersList.internshipEndDate")}</th>
                            </tr>
                        </thead>
                        <tbody>
                            {offers.map((offer) => {return <OfferRow key={offer.id} offer={offer} />})}
                        </tbody>
                    </Table>
                    :
                    <p>{t("offersList.noOffers")}</p>
            }
        </>
    )
}

export default OffersList;