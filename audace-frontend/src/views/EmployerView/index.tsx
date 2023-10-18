import {Container} from "react-bootstrap";
import {useTranslation} from "react-i18next";
import OffersList from "../../components/OffersList";
import { useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import { Offer } from "../../model/offer";
import { Employer, UserType } from "../../model/user";
import { getUserId } from "../../services/authService";
import { getEmployerById } from "../../services/userService";
import { getAllOffersByEmployerId } from "../../services/offerService";

const EmployerView = () => {
    const [employer, setEmployer] = useState<Employer>();
    const [offers, setOffers] = useState<Offer[]>([]);
    const [error, setError] = useState<string>("");
    const {t} = useTranslation();
    const navigate = useNavigate();

    useEffect(() => {
        if (employer !== undefined) return;
        const id = getUserId();
        if (id == null) {
            navigate("/pageNotFound");
            return;
        }

        getEmployerById(parseInt(id!))
            .then((res) => {
                setEmployer(res.data);
            })
            .catch((err) => {
                console.log(err)
                if (err.request.status === 404) setError(t("employer.errors.employerNotFound"));
            })
    }, [employer, navigate, t]);

    useEffect(() => {
        if (employer === undefined) return;

        getAllOffersByEmployerId(employer.id!)
            .then((res) => {
                setOffers(res.data);
            })
            .catch((err) => {
                console.log(err)
            })
    }, [employer]);

    return (
        <Container>
            <h1>{t("studentOffersList.viewTitle")}</h1>
            <OffersList offers={offers} error={error} userType={UserType.Employer} />
        </Container>
    )
}

export default EmployerView;