import {useTranslation} from "react-i18next";
import {useEffect, useState} from "react";
import {Offer} from "../../model/offer";
import {getUserId} from "../../services/authService";
import {useNavigate} from "react-router-dom";
import Application, {ApplicationStatus} from "../../model/application";
import {Container} from "react-bootstrap";
import {getAllApplicationsByEmployerIdAndOfferId} from "../../services/applicationService";
import ApplicationsList from "../ApplicationsList";
import {UserType} from "../../model/user";

interface Props {
    offer: Offer;
    userType: UserType;
    updateAvailablePlaces?: (offer: Offer) => void;
}

const Applications = ({offer, userType, updateAvailablePlaces} : Props) => {
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
        getAllApplicationsByEmployerIdAndOfferId(parseInt(id), offer.id!)
            .then((res) => {
                setApplications(res.data);
            })
            .catch((err) => {
                setError(err.response.data);
                console.log(err)
            })
    }, [navigate, t, offer.id]);

    const updateApplicationsState = (application: Application, applicationStatus: ApplicationStatus) => {
        let newApplications = applications.filter((a) => a.id !== application.id);
        application.applicationStatus = applicationStatus
        newApplications.push(application);
        setApplications(newApplications);
        if (applicationStatus == ApplicationStatus.ACCEPTED) updateAvailablePlaces!(offer);
    };

    return (
        <Container>
            <h1 className="text-center my-3">{offer.title}</h1>
            {<ApplicationsList applications={applications} error={error} userType={userType} updateApplicationsState={updateApplicationsState} />}
        </Container>
    );
};

export default Applications;