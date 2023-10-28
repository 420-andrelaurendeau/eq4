import {UserType} from "../../../model/user";
import { useTranslation } from "react-i18next";
import { ReactElement, useEffect, useState } from "react";
import { Offer } from "../../../model/offer";
import {getUserId} from "../../../services/authService";
import {useNavigate} from "react-router-dom";
import Application from "../../../model/application";
import { CV } from "../../../model/cv";
import CvList from "../../../components/CVsList";
import {Container} from "react-bootstrap";
import { getAllApplicationsByEmployerId } from "../../../services/applicationService";
import { getAllOffersByEmployerIdAndSessionId } from "../../../services/offerService";

const EmployerApplicationView = () => {
    const [error, setError] = useState<string>("");
    const [cvs, setCvs] = useState<Map<Offer, Application[]>>(new Map<Offer, Application[]>());
    const {t} = useTranslation();
    const [cvsApplicationsApplied, setCvsApplicationsApplied] = useState<boolean>(false);
    const navigate = useNavigate();

    useEffect(() => {
        const id = getUserId();
        if (id == null) {
            navigate("/pageNotFound");
            return;
        }
        if (cvs.size !== 0) return;
        console.log("HERE ")

        getAllOffersByEmployerIdAndSessionId(parseInt(id))
            .then((res) => {
                let map : Map<Offer, Application[]> = new Map<Offer, Application[]>();
                res.data.forEach((offer) => {
                    map.set(offer, []);
                })
                setCvs(map);
            })
            .catch((err) => {
                setError(err.response.data);
                console.log(err)
            })
    }, [navigate, cvs]);

    useEffect(() => {
        const id = getUserId();
        if (id == null) {
            navigate("/pageNotFound");
            return;
        }
        if (cvs.size === 0) return;

        if (cvsApplicationsApplied) return;

        getAllApplicationsByEmployerId(parseInt(id!))
            .then((res) => {
                const dataMap = new Map(Object.entries(res.data));
                let map2 = new Map(cvs);
                map2.forEach((value, key) => {
                    if (dataMap.has(key.id!.toString())) {
                        map2.set(key, dataMap.get(key.id!.toString()));
                    }
                })
                setCvs(map2);
                setCvsApplicationsApplied(true);
            })
            .catch((err) => {
                setError(err.response.data);
                console.log(err)
            })
    }, [navigate, t, cvs, cvsApplicationsApplied]);

    const getReactElements = () => {
        let elements : ReactElement<any, any>[] = []
        cvs.forEach((value, key) => {elements.push(makeOfferList(value, key))});
        return elements
    }

    const makeOfferList = (value : Application[], key : Offer) => {
        let cv : CV[] = [];
        value.forEach((application) => {cv.push(application.cv!)});
        return (
            <div key={key.id}>
                <h2>{key.title}</h2>
                <CvList cvs={cv} error={error} userType={UserType.Employer} />
            </div>
        )
    }

    return (
        <Container>
            <h1 className="text-center my-3">{t("employerCvsList.title")}</h1>
            {getReactElements()}
        </Container>
    );
};

export default EmployerApplicationView;