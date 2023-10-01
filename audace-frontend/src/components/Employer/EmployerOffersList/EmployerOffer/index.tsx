import {useTranslation} from "react-i18next";
import {Offer} from "../../../../model/offer";
import {formatDate} from "../../../../services/formatService";
import StudentOfferModal from "../../../StudentOffersList/StudentOffer/StudentOfferModal";
import {useState} from "react";
import EmployerOfferModal from "./OfferModal";
import {Employer} from "../../../../model/user";

interface Props {
    offer: Offer;
}
const EmployerOffer = ({offer}: Props) => {
    const [show, setShow] = useState<boolean>(false);

    const handleClick = () => setShow(true);
    const handleClose = () => setShow(false);

    return (
        <>
            <tr className="hovered" onClick={handleClick}>
                <td>{offer.title}</td>
                <td>{offer.internshipStartDate}</td>
                <td>{offer.internshipEndDate}</td>
            </tr>
            {show && <EmployerOfferModal offer={offer} show={show} handleClose={handleClose}/>}
        </>
    );

}
export default EmployerOffer;