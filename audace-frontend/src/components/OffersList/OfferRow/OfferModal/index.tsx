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

const OfferModal = ({offer, show, handleClose}: Props) => {
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
                        <div>{t("offer.modal.org")}: {
                                createBoldText(
                                    employer !== undefined ? 
                                    employer.organisation! : 
                                    t("offer.modal.orgNotFound")
                                )
                            }
                        </div>
                        <div>{t("offer.modal.address")}:&nbsp;
                            {createBoldText(
                                employer !== undefined ? 
                                employer.address! : 
                                t("offer.modal.orgNotFound")
                            )}
                        </div>
                        <div>{t("offer.modal.phone")}:&nbsp;
                            {createBoldText(
                                employer !== undefined ? 
                                employer.phone! : 
                                t("offer.modal.orgNotFound")
                            )}
                        </div>
                    </div>

                    <hr/>

                    <u><h4 className="my-3 text-center">{t("offer.modal.offerDescription")}</h4></u>
                    <div style={{textAlign : "justify"}}>{offer.description}</div>

                    <hr/>

                    <div className="text-end">
                        <div>
                            {t("offer.modal.internDate.start")}:&nbsp;
                            {createBoldText(formatDate(offer.internshipStartDate))}&nbsp;
                            {t("offer.modal.internDate.end")}:&nbsp;
                            {createBoldText(formatDate(offer.internshipEndDate))}
                        </div>
                        <div>{t("offer.modal.offerEnd")}: {createBoldText(formatDate(offer.offerEndDate))}</div>
                    </div>
                </Modal.Body>
                <Modal.Footer>
                    {employer === undefined && <div className="text-danger">{t("offer.modal.empNotFound")}</div>}
                    <Button className="btn-success" onClick={applyToOffer} disabled={employer === undefined}>{t("offer.modal.apply")}</Button>
                </Modal.Footer>
            </Modal>
        </>
    );
};

export default OfferModal;