import { Offer, OfferStatus } from "../../model/offer";
import { useTranslation } from "react-i18next";
import OfferRow from "./OfferRow";
import { UserType } from "../../model/user";
import GenericTable from "../GenericTable";
import { useSessionContext } from "../../contextsholders/providers/SessionContextHolder";
import Accordion from 'react-bootstrap/Accordion';
import Applications from "../Applications";
import { Card } from "react-bootstrap";

interface Props {
    offers: Offer[];
    error: string;
    userType: UserType;
    updateOffersState?: (offer: Offer, offerStatus: OfferStatus) => void;
    seeApplications?: (offer: Offer) => void;
    updateAvailablePlaces?: (offer: Offer) => void;
}

const OffersList = ({ offers, error, userType, updateOffersState, seeApplications, updateAvailablePlaces }: Props) => {
    const { t } = useTranslation();
    const { chosenSession, currentSession } = useSessionContext();

    return (
        <>
        <Accordion defaultActiveKey="0">
            <GenericTable list={offers} error={error} emptyListMessage="offersList.noOffers" title="studentOffersList.viewTitle">
                <thead>
                    <tr>
                        <th>{t("offersList.title")}</th>
                        <th>{t("offersList.internshipStartDate")}</th>
                        <th>{t("offersList.internshipEndDate")}</th>
                        <th>{t("offersList.availablePlaces")}</th>
                        {chosenSession?.id === currentSession?.id && <th></th>}
                    </tr>
                </thead>
                <tbody>
                    {offers.map((offer: Offer) => {
                        return(
                            <>
                                <OfferRow key={offer.id} offer={offer} userType={userType} updateOffersState={updateOffersState} seeApplications={seeApplications}/>
                                {userType != UserType.Student &&
                                    <tr>
                                        <td colSpan={12}>
                                            <Accordion.Collapse eventKey={offer.id!.toString()}>
                                                <Card.Body>
                                                    <Applications offer={offer} userType={userType} updateAvailablePlaces={updateAvailablePlaces}/>
                                                </Card.Body>
                                            </Accordion.Collapse>
                                        </td>
                                    </tr>
                                }

                            </>
                            )
                        })
                    }

                </tbody>
            </GenericTable>
        </Accordion>
        </>
    );
};

export default OffersList;
