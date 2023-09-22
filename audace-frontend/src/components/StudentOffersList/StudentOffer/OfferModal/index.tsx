import { useEffect, useState } from "react";
import { Offer } from "../../../../model/offer";
import { Employer } from "../../../../model/user";
import { Button, Modal } from "react-bootstrap";
import { formatDate } from "..";
import { getEmployerById } from "../../../../services/userService";

interface Props {
    offer: Offer;
    show: boolean;
    handleClose: () => void;
}

const OfferModal = ({offer, show, handleClose}: Props) => {
    const [employer, setEmployer] = useState<Employer | undefined>(undefined);

    useEffect(() => {
        getEmployerById(offer.employerId)
            .then((res) => {
                setEmployer(res.data);
            })
            .catch((err) => {
                console.log(err);
            });
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
                        <div>Organization: {
                                createBoldText(
                                    employer !== undefined ? 
                                    employer.organisation! : 
                                    "Organization not found!"
                                )
                            }
                        </div>
                        <div>From : {createBoldText(formatDate(offer.internshipStartDate))} to <b>{createBoldText(formatDate(offer.internshipEndDate))}</b></div>
                        <div>Offer ends: {createBoldText(formatDate(offer.offerEndDate))}</div>
                    </div>

                    <hr/>

                    <u><h4 className="my-3 text-center">Offer description</h4></u>
                    <div style={{textAlign : "justify"}}>{offer.description}</div>
                </Modal.Body>
                <Modal.Footer>
                    {employer === undefined && <div className="text-danger">Employer not found! You cannot apply for this internship!</div>}
                    <Button className="btn-success" onClick={applyToOffer} disabled={employer === undefined}>Apply</Button>
                </Modal.Footer>
            </Modal>
        </>
    );
};

export default OfferModal;