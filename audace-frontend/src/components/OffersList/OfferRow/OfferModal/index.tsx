import { useEffect } from "react";
import { Offer, OfferStatus } from "../../../../model/offer";
import { Employer, UserType } from "../../../../model/user";
import { Modal } from "react-bootstrap";
import { getEmployerById } from "../../../../services/userService";
import { useTranslation } from "react-i18next";
import { formatDate } from "../../../../services/formatService";
import OfferButtons from "../OfferButtons";

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

    useEffect(() => {
        if (employer !== undefined) return;

        getEmployerById(offer.employerId)
            .then((res) => {
                setEmployer!(res.data);
            })
            .catch((err) => {
                console.log(err);
            });
    }, [setEmployer, offer, employer]);

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
                        {userType === UserType.Employer &&
                            <div>
                                {t("employerOffersList.status")}:&nbsp;
                                {createBoldText(t(`employerOffersList.${offer.status}`))}
                            </div>
                        }
                    </div>
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