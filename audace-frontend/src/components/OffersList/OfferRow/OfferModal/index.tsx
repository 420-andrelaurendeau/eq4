import {useEffect, useState} from "react";
import { Offer, OfferStatus } from "../../../../model/offer";
import { Employer, UserType } from "../../../../model/user";
import {Button, Modal} from "react-bootstrap";
import { getEmployerById } from "../../../../services/userService";
import { useTranslation } from "react-i18next";
import { formatDate } from "../../../../services/formatService";
import { apply } from "../../../../services/studentApplicationService"
import { useParams} from "react-router";
import axios from "axios";
import Application from "../../../../model/application";

interface Props {
    offer: Offer;
    show: boolean;
    handleClose: () => void;
    userType: UserType;
    employer?: Employer;
    setEmployer?: (employer: Employer) => void;
    updateOffersState?: (offer : Offer, offerStatus : OfferStatus) => void;
}

const OfferModal = ({offer, show, handleClose, userType, employer, setEmployer, updateOffersState}: Props) => {
    const {t} = useTranslation();
    const { id: studentIdFromURL } = useParams();
    const studentId = parseInt(studentIdFromURL!, 10);

    const [applicationMessage, setApplicationMessage] = useState("");
    const [applicationMessageColor, setApplicationMessageColor] = useState("");

    useEffect(() => {
        if (employer !== undefined) return;
        console.log("useEffect is being called again");

        getEmployerById(offer.employerId)
            .then((res) => {
                setEmployer!(res.data);
            })
            .catch((err) => {
                console.log("getEmployerById error", err);
            });
    }, [setEmployer, offer, employer]);

    const createBoldText = (text: string) => {
        return <b>{text}</b>;
    };

    const handleApply = () => {
        const applicationData: Application = {
            id: 1000,
            studentId: studentId,
            offerId: offer.id,
            cvId: 1,
        };

        apply(applicationData)
            .then((response) => {
                setApplicationMessage("Application submitted successfully");
                setApplicationMessageColor("green");
            })
            .catch((error) => {
                setApplicationMessage("Error submitting application: " + error.message);
                setApplicationMessageColor("red");
            });
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
                    {applicationMessage && (
                        <div style={{ color: applicationMessageColor }}>{applicationMessage}</div>
                    )}
                    <Button onClick={() => handleApply()}>Apply</Button>
                    {/*<OfferButtons userType={userType} disabled={employer === undefined} offer={offer} updateOffersState={updateOffersState}/>*/}
                </Modal.Footer>
            </Modal>
        </>
    );
};

export default OfferModal;