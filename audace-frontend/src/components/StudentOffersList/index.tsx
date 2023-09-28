import { Offer } from "../../model/offer";
import StudentOffer from "./StudentOffer";
import { Table } from "react-bootstrap";
import { useTranslation } from "react-i18next";

interface Props {
    offers: Offer[];
    error: string;
}

const StudentOffersList = ({offers, error}: Props) => {
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