import { useTranslation } from "react-i18next";
import { useEffect, useState } from "react";
import { Offer } from "../../model/offer";
import {getUserId} from "../../services/authService";
import {useNavigate} from "react-router-dom";
import Application from "../../model/application";
import {Container} from "react-bootstrap";
import { getAllApplicationsByEmployerIdAndOfferId } from "../../services/applicationService";
import ApplicationsList from "../ApplicationsList";

interface Props {
    offer: Offer;
}

const Applications = ({offer} : Props) => {
    const [error, setError] = useState<string>("");
    const [applications, setApplications] = useState<Application[]>([]);
    const {t} = useTranslation();
    const navigate = useNavigate();

    useEffect(() => {
        const id = getUserId();
        if (id == null) {
            navigate("/pageNotFound");
            return;
        }
        getAllApplicationsByEmployerIdAndOfferId(offer.id!)
            .then((res) => {
                setApplications(res.data);
            })
            .catch((err) => {
                setError(err.response.data);
                console.log(err)
            })
    }, [navigate, t, offer.id]);

    return (
        <Container>
            <h1 className="text-center my-3">{offer.title}</h1>
            {<ApplicationsList applications={applications} error={error} />}
        </Container>
    );
};

export default Applications;