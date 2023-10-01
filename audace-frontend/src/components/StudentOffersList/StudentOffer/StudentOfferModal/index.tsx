import { useEffect, useState } from "react";
import { Offer } from "../../../../model/offer";
import { Employer } from "../../../../model/user";
import { Button, Modal } from "react-bootstrap";
import { getEmployerById } from "../../../../services/userService";
import { useTranslation } from "react-i18next";
import { formatDate } from "../../../../services/formatService";

interface Props {
    offer: Offer;
    show: boolean;
    handleClose: () => void;
}

const StudentOfferModal = ({offer, show, handleClose}: Props) => {
    const {t} = useTranslation();
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
                        <div>{t("studentOffer.modal.org")}: {
                                createBoldText(
                                    employer !== undefined ? 
                                    employer.organisation! : 
                                    t("studentOffer.modal.orgNotFound")
                                )
                            }
                        </div>
                        <div>{t("studentOffer.modal.address")}:&nbsp;
                            {createBoldText(
                                employer !== undefined ? 
                                employer.address! : 
                                t("studentOffer.modal.orgNotFound")
                            )}
                        </div>
                        <div>{t("studentOffer.modal.phone")}:&nbsp;
                            {createBoldText(
                                employer !== undefined ? 
                                employer.phone! : 
                                t("studentOffer.modal.orgNotFound")
                            )}
                        </div>
                    </div>

                    <hr/>

                    <u><h4 className="my-3 text-center">{t("studentOffer.modal.offerDescription")}</h4></u>
                    <div style={{textAlign : "justify"}}>{offer.description}</div>

                    <hr/>

                    <div className="text-end">
                        <div>
                            {t("studentOffer.modal.internDate.start")}:&nbsp;
                            {createBoldText(offer.internshipStartDate)}&nbsp;
                            {t("studentOffer.modal.internDate.end")}:&nbsp;
                            {createBoldText(offer.internshipEndDate)}
                        </div>
                        <div>{t("studentOffer.modal.offerEnd")}: {createBoldText(offer.offerEndDate)}</div>
                    </div>
                </Modal.Body>
                <Modal.Footer>
                    {employer === undefined && <div className="text-danger">{t("studentOffer.modal.empNotFound")}</div>}
                    <Button className="btn-success" onClick={applyToOffer} disabled={employer === undefined}>{t("studentOffer.modal.apply")}</Button>
                </Modal.Footer>
            </Modal>
        </>
    );
};

export default StudentOfferModal;