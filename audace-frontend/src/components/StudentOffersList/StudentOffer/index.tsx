import { useState } from "react";
import { Offer } from "../../../model/offer";
import OfferModal from "./OfferModal";
import './styles.css'

interface Props {
    offer: Offer;
}

const StudentOffer = ({offer}: Props) => {
    const [show, setShow] = useState<boolean>(false);

    const formatDate = (date: Date) => {
        const newDate = new Date(date);
        const year = newDate.getFullYear();
        const month = newDate.getMonth() + 1;
        const day = newDate.getDate();

        return `${day}/${month}/${year}`;
    }

    const handleClick = () => {
        setShow(true);
    };

    const handleClose = () => {
        setShow(false);
    };

    return (
        <>
            <tr onClick={handleClick}>
                <td>{offer.title}</td>
                <td>{formatDate(offer.internshipStartDate)}</td>
                <td>{formatDate(offer.internshipEndDate)}</td>
            </tr>
            <OfferModal offer={offer} handleClose={handleClose} show={show}/>
        </>
    );
};

export default StudentOffer;