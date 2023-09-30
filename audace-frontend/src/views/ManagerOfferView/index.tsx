import { Container } from "react-bootstrap";
import { Manager, UserType } from "../../model/user";
import { Department } from "../../model/department";
import { useTranslation } from "react-i18next";
import { useEffect, useState } from "react";
import { Offer, OfferStatus } from "../../model/offer";
import OffersList from "../../components/OffersList";
import { getManagerOffersByDepartment } from "../../services/offerService";

// Temp until login works

const tempDepartment: Department = {
    id: 1,
    name: "",
    code: "",
}

export const tempManager: Manager = {
    id: 1,
    email: "",
    password: "",
    department: tempDepartment,
}

interface Props {
    manager: Manager;
}

const ManagerOfferView = ({manager}: Props) => {
    const [offers, setOffers] = useState<Offer[]>([]);
    const [offersAccepted, setOffersAccepted] = useState<Offer[]>([]);
    const [offersRefused, setOffersRefused] = useState<Offer[]>([]);
    const [error, setError] = useState<string>("");
    const {t} = useTranslation();

    useEffect(() => {
        getManagerOffersByDepartment(manager.department!.id!)
        .then((res) => {
            let acceptedOffers = [];
            let refusedOffers = [];
            let offers = [];
            for (let i = 0; i < res.data.length; i = i + 1) {
                if (res.data[i].status === "ACCEPTED") {
                    acceptedOffers.push(res.data[i]);
                }
                else if (res.data[i].status === "REFUSED") {
                    refusedOffers.push(res.data[i]);
                }
                else if (res.data[i].status === "PENDING") {
                    offers.push(res.data[i]);
                }
            }
            setOffersAccepted(acceptedOffers);
            setOffersRefused(refusedOffers);
            setOffers(offers);
        })
        .catch((err) => {
            console.log(err)
            if (err.request.status === 404) setError(t("offersList.errors.departmentNotFound"));
        })
    }, [manager.department, t]);

    const updateOffersState = (offer : Offer, offerStatus : OfferStatus) => {
        let newOffers = offers.filter((o) => o.id !== offer.id);
        offer.status = offerStatus;
        setOffers(newOffers);
        if (offerStatus === "ACCEPTED") {
            setOffersAccepted([...offersAccepted, offer]);
        }
        else if (offerStatus === "REFUSED") {
            setOffersRefused([...offersRefused, offer]);
        }
    }
    return (
        <Container>
            <h1>{t("managerOffersList.viewTitle")}</h1>
            {offers.length > 0 ? <OffersList offers={offers} error={error} userType={UserType.Manager} updateOffersState={updateOffersState}/> : <p>No more pending offers</p>}
            {offersAccepted.length > 0 ? <OffersList offers={offersAccepted} error={error} userType={UserType.Manager}/> : null}
            {offersRefused.length > 0 ? <OffersList offers={offersRefused} error={error} userType={UserType.Manager}/> : null}
        </Container>
    );
};

export default ManagerOfferView;