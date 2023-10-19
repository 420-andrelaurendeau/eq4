import { useState } from "react";
import { Offer, OfferStatus } from "../../../model/offer";
import OfferModal from "./OfferModal";
import './styles.css'
import { formatDate } from "../../../services/formatService";
import { Employer, UserType } from "../../../model/user";
import OfferButtons from "./OfferButtons";


interface Props {
    offer: Offer;
    userType: UserType;
    updateOffersState?: (offer : Offer, offerStatus : OfferStatus) => void;
}

const OfferRow = ({offer, userType, updateOffersState}: Props) => {
    const [show, setShow] = useState<boolean>(false);
    const handleClick = () => setShow(true);
    const handleClose = () => setShow(false);
    const [employer, setEmployer] = useState<Employer | undefined>(undefined);

    return (
        <>
            <tr className="hovered" onClick={handleClick}>
                <td>{offer.title}</td>
                <td>{formatDate(offer.internshipStartDate)}</td>
                <td>{formatDate(offer.internshipEndDate)}</td>
                <td className="text-end"><OfferButtons userType={userType} disabled={employer === undefined} offer={offer} updateOffersState={updateOffersState}/></td>
            </tr>
            {show && <OfferModal offer={offer} show={show} handleClose={handleClose} userType={userType} employer={employer} setEmployer={setEmployer} updateOffersState={updateOffersState}/>}
        </>
    );
};

export default OfferRow;