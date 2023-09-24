import { useState } from "react";
import { Offer } from "../../../model/offer";
import OfferModal from "./OfferModal";
import './styles.css'
import { formatDate } from "../../../services/formatService";

interface Props {
    offer: Offer;
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