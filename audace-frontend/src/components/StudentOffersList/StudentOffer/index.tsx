import { useState } from "react";
import { Offer } from "../../../model/offer";
import OfferModal from "./OfferModal";
import './styles.css'

interface Props {
    offer: Offer;
}

export const formatDate = (date: Date) => {
    const newDate = new Date(date);
    const year = newDate.getFullYear();
    const month = newDate.getMonth() + 1;
    const day = newDate.getDate();

    return `${day}/${month}/${year}`;
}

const StudentOffer = ({offer}: Props) => {
    const [show, setShow] = useState<boolean>(false);

    const handleClick = () => setShow(true);
    const handleClose = () => setShow(false);

    return (
        <>
            <tr className="hovered" onClick={handleClick}>
                <td>{offer.title}</td>
                <td>{formatDate(offer.internshipStartDate)}</td>
                <td>{formatDate(offer.internshipEndDate)}</td>
            </tr>
            {show && <OfferModal offer={offer} show={show} handleClose={handleClose}/>}
        </>
    );
};

export default StudentOffer;