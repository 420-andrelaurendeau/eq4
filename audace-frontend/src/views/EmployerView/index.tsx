import { Container } from "react-bootstrap";
import {useEffect, useState} from "react";
import {Employer, UserType} from "../../model/user";
import {useNavigate, useParams} from "react-router-dom";
import {Offer} from "../../model/offer";
import {useTranslation} from "react-i18next";
import {getEmployerById} from "../../services/userService";
import {getAllOffersByEmployerId} from "../../services/offerService";
import OffersList from "../../components/OffersList";
import {getUserId} from "../../services/authService";

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
            <h1 className="my-3">Employer view</h1>
            <OffersList offers={offers} error={error} userType={UserType.Employer} />
        </Container>
    )
}

export default EmployerView;