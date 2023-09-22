import { useEffect, useState } from "react";
import { Offer } from "../../../../model/offer";
import { Employer } from "../../../../model/user";
import { Button, Modal } from "react-bootstrap";
import { formatDate } from "..";

interface Props {
    offer: Offer;
    show: boolean;
    handleClose: () => void;
}

const OfferModal = ({offer, show, handleClose}: Props) => {
    const [employer, setEmployer] = useState<Employer>();

    useEffect(() => {
        let employer: Employer = {
            id: offer.employerId,
            firstName: "John",
            lastName: "Doe",
            address: "123 Fake Street",
            phone: "1234567890",
            email: "",
            password: "",
            organisation: "Fake Organisation",
            extension: "fake",
            position: "Fake Position",
            offers: []
        }

        setEmployer(employer);
    }, [setEmployer, offer]);

    const applyToOffer = () => {
        // Not in this story's scope
    };

    const createBoldText = (text: string) => {
        return <b>{text}</b>;
    };

    return (
        <>
            <Modal show={show} onHide={handleClose} size="lg">
                <Modal.Header closeButton>
                    <Modal.Title>{offer.title}</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <div className="text-end">
                        <div>Organization: {createBoldText(employer?.organisation!)}</div>
                        <div>From : {createBoldText(formatDate(offer.internshipStartDate))} to <b>{createBoldText(formatDate(offer.internshipEndDate))}</b></div>
                        <div>Offer ends: {createBoldText(formatDate(offer.offerEndDate))}</div>
                    </div>

                    <hr/>

                    <u><h4 className="my-3 text-center">Offer description</h4></u>
                    <div style={{textAlign : "justify"}}>{offer.description}</div>
                </Modal.Body>
                <Modal.Footer>
                    <Button className="btn-success" onClick={applyToOffer}>Apply</Button>
                </Modal.Footer>
            </Modal>
        </>
    );
};

export default OfferModal;