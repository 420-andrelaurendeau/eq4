import {useEffect, useState} from "react";
import { Offer, OfferStatus } from "../../../model/offer";
import OfferModal from "./OfferModal";
import './styles.css'
import { formatDate } from "../../../services/formatService";
import {Employer, UserType} from "../../../model/user";
import OfferButtons from "./OfferButtons";
import {getEmployerById} from "../../../services/userService";
import {getUserId} from "../../../services/authService";


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
    const studentId = getUserId();

    useEffect(() => {
        if (employer !== undefined) return;
        getEmployerById(offer.employer.id!)
            .then((res) => {
                setEmployer!(res.data);
            })
            .catch((err) => {
                console.log("getEmployerById error", err);
            });
    }, [setEmployer, offer, employer, studentId]);

    return (
        <>
            <tr className="hovered" onClick={handleClick}>
                <td>{offer.title}</td>
                <td>{formatDate(offer.internshipStartDate)}</td>
                <td>{formatDate(offer.internshipEndDate)}</td>
                <td className="text-center"><OfferButtons userType={userType} disabled={employer === undefined} offer={offer} updateOffersState={updateOffersState} /></td>
            </tr>
            {show && <OfferModal offer={offer} show={show} handleClose={handleClose} userType={userType} employer={employer} setEmployer={setEmployer} updateOffersState={updateOffersState}/>}
        </>
    );
};

export default OfferRow;