import {Employer, Student, UserType} from "../../../model/user";
import { useTranslation } from "react-i18next";
import { ReactElement, useEffect, useState } from "react";
import { Offer, OfferStatus } from "../../../model/offer";
import {getEmployerById,} from "../../../services/userService";
import {getUserId} from "../../../services/authService";
import {useNavigate} from "react-router-dom";
import Application from "../../../model/application";
import { CV, CVStatus } from "../../../model/cv";
import CvList from "../../../components/CVsList";
import {Container} from "react-bootstrap";
import { Department } from "../../../model/department";

const EmployerApplicationView = () => {
    const [employer, setEmployer] = useState<Employer>();
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

    const department : Department = {id : 1, code : "code", name : "COMPUTER SCIENCE"};
    const student : Student = {id : 1, firstName : "firstName", lastName : "lastName", email : "email", phone : "phone", address : "address", password : "oh no", type : "type", studentNumber : "This is a number string", department : department};

    const offer : Offer = {id : 1, title : "offre", description : "description", internshipStartDate : new Date(Date.now()), internshipEndDate : new Date(Date.now() + 100000), offerEndDate : new Date(Date.now() + 100000), availablePlaces : 5, department : department, employer : employer!, offerStatus : OfferStatus.PENDING}
    const offer2 : Offer = {id : 1, title : "offre", description : "description", internshipStartDate : new Date(Date.now()), internshipEndDate : new Date(Date.now() + 100000), offerEndDate : new Date(Date.now() + 100000), availablePlaces : 5, department : department, employer : employer!, offerStatus : OfferStatus.PENDING}

    const application : Application[] = [{id : 1, student : student, offer : offer, cv : {id : 1, fileName : "string", content : "content", student : student, cvStatus : CVStatus.PENDING}}]
    const application2 : Application[] = [{id : 1, student : student, offer : offer, cv : {id : 1, fileName : "string", content : "content", student : student, cvStatus : CVStatus.PENDING}}]

    const tempBackEndStuff : Map<Offer, Application[]> = new Map<Offer, Application[]>([
        [offer, application],
        [offer2, application2]
    ]);

    const getReactElements = () => {
        let elements : ReactElement<any, any>[] = []
        tempBackEndStuff.forEach((value, key) => {elements.push(makeOfferList(value, key))});
        return elements
    }

    const makeOfferList = (value : Application[], key : Offer) => {
        let cv : CV[] = [];
        value.forEach((application) => {cv.push(application.cv!)});
        return (
            <div>
                <h1>{key.title}</h1>
                <CvList cvs={cv} error={error} userType={UserType.Employer} />
            </div>
        )
    }

    return (
    <Container>
        {getReactElements()}
    </Container>
    );
};

export default EmployerApplicationView;