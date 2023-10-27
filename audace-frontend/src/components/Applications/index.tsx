import { UserType } from "../../model/user";
import { useTranslation } from "react-i18next";
import { ReactElement, useEffect, useState } from "react";
import { Offer } from "../../model/offer";
import {getUserId} from "../../services/authService";
import {useNavigate} from "react-router-dom";
import Application from "../../model/application";
import { CV } from "../../model/cv";
import CvsList from "../../components/CVsList";
import {Container} from "react-bootstrap";
import { getAllApplicationsByOfferId } from "../../services/applicationService";

interface Props {
    offer: Offer;
}

const Applications = ({offer} : Props) => {
    const [error, setError] = useState<string>("");
    const [applications, setApplications] = useState<Application[]>([]);
    const [cvs, setCvs] = useState<CV[]>([]);
    const {t} = useTranslation();
    const navigate = useNavigate();

    useEffect(() => {
        const id = getUserId();
        if (id == null) {
            navigate("/pageNotFound");
            return;
        }
        if (applications.length === 0) {
            return;
        };
        getAllApplicationsByOfferId(offer.id!)
            .then((res) => {
                setApplications(res.data);
                res.data.forEach((application) => {
                    if (application.cv !== undefined) {
                        setCvs([...cvs, application.cv]);
                    }
                })
            })
            .catch((err) => {
                setError(err.response.data);
                console.log(err)
            })
    }, [navigate, t, applications, offer.id, cvs]);

    return (
        <Container>
            <h1 className="text-center my-3">{offer.title}</h1>
            {<CvsList cvs={cvs} error={error} userType={UserType.Employer} />}
        </Container>
    );
};

export default Applications;