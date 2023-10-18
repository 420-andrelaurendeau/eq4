import { useEffect, useState } from "react";
import { Offer, OfferStatus } from "../../../../model/offer";
import { Employer, Student, UserType } from "../../../../model/user";
import { Button, Col, Modal, Row } from "react-bootstrap";
import { getEmployerById } from "../../../../services/userService";
import { useTranslation } from "react-i18next";
import { formatDate } from "../../../../services/formatService";
import { apply } from "../../../../services/studentApplicationService"
import { useParams } from "react-router";
import Application from "../../../../model/application";
import { CV } from "../../../../model/cv";
import { getUserId } from "../../../../services/authService";
import OfferButtons from "../OfferButtons";

interface Props {
    offer: Offer;
    show: boolean;
    handleClose: () => void;
    userType: UserType;
    employer?: Employer;
    setEmployer?: (employer: Employer) => void;
    updateOffersState?: (offer: Offer, offerStatus: OfferStatus) => void;
}

const OfferModal = ({ offer, show, handleClose, userType, employer, setEmployer, updateOffersState }: Props) => {
    const { t } = useTranslation();
    const [applicationMessage, setApplicationMessage] = useState("");
    const [applicationMessageColor, setApplicationMessageColor] = useState("");

    useEffect(() => {
        if (employer !== undefined) return;

        getEmployerById(offer.employer.id!)
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
        const studentId = getUserId();

        if (!studentId) return;

        const tempStudent: Student = {
            id: parseInt(studentId),
            email: "",
            password: "",
            firstName: "",
            lastName: "",
            type: "student",
            phone: "",
            address: "",
            studentNumber: "",
        }
        const tempCV: CV = {
            id: 1,
            student: tempStudent,
            fileName: "CV",
            content: "test",
        }
        const applicationData: Application = {
            id: 1000,
            student: tempStudent,
            offer: offer,
            cv: tempCV,
        };

        apply(applicationData).then((response) => {
                setApplicationMessage("Application submitted successfully");
                setApplicationMessageColor("green");
        }).catch((error) => {
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
                    <Row>
                        <Col>
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
                        </Col>
                        <Col>
                            <div className="text-sm-end mt-2">
                                <div>
                                    {t("offer.modal.internDate.start")}:&nbsp;
                                    {createBoldText(formatDate(offer.internshipStartDate))}&nbsp;
                                    {t("offer.modal.internDate.end")}:&nbsp;
                                    {createBoldText(formatDate(offer.internshipEndDate))}
                                </div>
                                <div>{t("offer.modal.offerEnd")}: {createBoldText(formatDate(offer.offerEndDate))}</div>
                            </div>
                        </Col>
                    </Row>
                    <hr />
                    <div style={{ textAlign: "justify" }}>{offer.description}</div>
                </Modal.Body>
                <Modal.Footer>
                    {employer === undefined && <div className="text-danger">{t("offer.modal.empNotFound")}</div>}
                    <OfferButtons userType={userType} disabled={employer === undefined} offer={offer} updateOffersState={updateOffersState}/>
                </Modal.Footer>
            </Modal>
        </>
    );
};

export default OfferModal;