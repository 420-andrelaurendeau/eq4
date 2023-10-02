import { useEffect, useState } from "react";
import {Offer} from "../../../../../model/offer";
import {useTranslation} from "react-i18next";
import {Employer} from "../../../../../model/user";
import {getEmployerById} from "../../../../../services/userService";
import {Button, Modal, Table} from "react-bootstrap";
import {useParams} from "react-router-dom";

interface Props {
    offer: Offer;
    show: boolean;
    handleClose: () => void;
}

const EmployerOfferModal = ({offer, show, handleClose}: Props) => {
    const {t} = useTranslation();
    const [employer, setEmployer] = useState<Employer>();

    useEffect(() => {
        getEmployerById(offer.employerId)
            .then((res) => {
                setEmployer(res.data);
            })
            .catch((err) => {
                console.log(err);
            });
    });


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
                    <div className="row">
                        <div className="text-start col-6">
                            <div>
                                {t("employerOffer.modal.internDate.start")}:&nbsp;{createBoldText(offer.internshipStartDate)}&nbsp;
                                {t("employerOffer.modal.internDate.end")}:&nbsp;{createBoldText(offer.internshipEndDate)}
                            </div>
                            <div>{t("employerOffer.modal.offerEnd")}: {createBoldText(offer.offerEndDate)}</div>
                        </div>

                        <div className="text-end col-5">
                            <div>{t("employerOffer.modal.org")}: {createBoldText(employer !== undefined ? employer.organisation! : t("employerOffer.modal.orgNotFound"))}</div>
                            <div>{t("studentOffer.modal.address")}:&nbsp;{createBoldText(employer !== undefined ? employer.address! : t("employerOffer.modal.orgNotFound"))}</div>
                            <div>{t("employerOffer.modal.phone")}:&nbsp;{createBoldText(employer !== undefined ? employer.phone! : t("employerOffer.modal.orgNotFound"))}</div>
                        </div>
                    </div>
                    <hr/>

                    <u><h5 className="my-3 text-center">{t("employerOffer.modal.offerDescription")}</h5></u>
                    <div style={{textAlign : "justify"}}>{offer.description}</div>

                    <hr/>
                    <div className="row">
                        <div className="col-6">{t("employerOffer.modal.offerAvailablePlaces")}: {createBoldText(offer.availablePlaces.toString())}</div>
                        <div className="col-6">{t("employerOffer.modal.status")}: {createBoldText(offer.status)}</div>

                    </div>


                </Modal.Body>
                <Modal.Footer>

                </Modal.Footer>
            </Modal>
        </>
    );
};

export default EmployerOfferModal;