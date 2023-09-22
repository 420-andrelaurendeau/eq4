import { useEffect, useState } from "react";
import { Offer } from "../../../../model/offer";
import { Employer } from "../../../../model/user";
import { Modal } from "react-bootstrap";

interface Props {
    offer: Offer;
    show: boolean;
    handleClose: () => void;
}

const OfferModal = ({offer, show, handleClose}: Props) => {
    const [employer, setEmployer] = useState<Employer>();

    useEffect(() => {

    });

    return (
        <>
            <Modal show={show} onHide={handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title>{offer.title}</Modal.Title>
                </Modal.Header>
            </Modal>
        </>
    );
};

export default OfferModal;