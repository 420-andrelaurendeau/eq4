import { Container } from "react-bootstrap";
import {Employer, UserType} from "../../../model/user";
import { useTranslation } from "react-i18next";
import { useEffect, useState } from "react";
import { Offer } from "../../../model/offer";
import OffersList from "../../../components/OffersList";
import { useParams } from "react-router-dom";
import {getEmployerById} from "../../../services/userService";
import {getAllOffersByEmployerId} from "../../../services/offerService";

const EmployerOfferView = () => {
    const [employer, setEmployer] = useState<Employer>();
    const {id} = useParams();
    const [offers, setOffers] = useState<Offer[]>([]);
    const [error, setError] = useState<string>("");
    const {t} = useTranslation();

    useEffect(() => {
        getEmployerById(parseInt(id!))
            .then((res) => {
                setEmployer(res.data);
            })
            .catch((err) => {
                console.log(err)
                if (err.request.status === 404) setError(t("employer.errors.employerNotFound"));
            })
    }, [employer, id, t]);

    useEffect(() => {
        if (employer === undefined) return;

        getAllOffersByEmployerId(employer.id!)
            .then((res) => {
                setOffers(res.data);
            })
            .catch((err) => {
                console.log(err)
            })
    }, [employer, t]);

    return (
        <Container>
            <h1>{t("studentOffersList.viewTitle")}</h1>
            <OffersList offers={offers} error={error} userType={UserType.Employer} />
        </Container>
    );
};

export default EmployerOfferView;