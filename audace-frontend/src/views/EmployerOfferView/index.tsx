import { Container } from "react-bootstrap";
import {Employer, Student, UserType} from "../../model/user";
import { useTranslation } from "react-i18next";
import { ReactElement, useEffect, useState } from "react";
import { Offer, OfferStatus } from "../../model/offer";
import OffersList from "../../components/OffersList";
import {getEmployerById,} from "../../services/userService";
import {getAllOffersByEmployerId} from "../../services/offerService";
import {getUserId} from "../../services/authService";
import {useNavigate} from "react-router-dom";
import Application from "../../model/application";
import { JsxElement } from "typescript";
import { CV } from "../../model/cv";
import CvList from "../../components/CVsList";

const EmployerOfferView = () => {
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

    const student : Student = {id : 1, firstName : "The", lastName : "Guy that repeatdly says to chill out", email : "it annoys me", phone : "cause their code is shit", address : "but then they tell people to chill out", password : "when they're pissed about it", type : "pain in my ass", studentNumber : "This is a number string", department : {id : 1, code : "fuck", name : "Why do I have to do this?"}};

    const offer : Offer = {id : 1, title : "offre", description : "description", internshipStartDate : new Date(Date.now()), internshipEndDate : new Date(Date.now() + 100000), offerEndDate : new Date(Date.now() + 100000), availablePlaces : 5, department : {id : 1, code : "fuck", name : "Why do I have to do this?"}, employer : employer!, status : OfferStatus.PENDING}
    
    const application : Application[] = [{id : 1, student : student, offer : offer, cv : {id : 1, fileName : "string", content : "content", student : student}}]
    
    const tempBackEndShitCauseItNotDone : Map<Offer, Application[]> = new Map<Offer, Application[]>([
        [offer, application]
    ]);

    const shitBroIDKHowToNameStuff = () => {
        let elements : ReactElement<any, any>[] = []
        console.log(tempBackEndShitCauseItNotDone.forEach((value, key) => {elements.push(makeOfferList(value, key))}));
        return elements
    }    

    const makeOfferList = (value : Application[], key : Offer) => {
        let cv : CV[] = [];
        value.forEach((application) => {cv.push(application.cv!)});
        return (
            <div>
                <h1>{key.title}</h1>
                <CvList offers={offer} error={error} userType={UserType.Employer} />
            </div>
        )
    }

    return (
    <div>
        {shitBroIDKHowToNameStuff()}
    </div>
    );
};

export default EmployerOfferView;