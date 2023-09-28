import { Container } from "react-bootstrap";
import { Manager } from "../../model/user";
import { Department } from "../../model/department";
import { useTranslation } from "react-i18next";
import { useEffect, useState } from "react";
import { getOffersByDepartment } from "../../services/offerService";
import { Offer } from "../../model/offer";
import OffersList from "../../components/OffersList";

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
    const [error, setError] = useState<string>("");
    const {t} = useTranslation();

    useEffect(() => {
        getOffersByDepartment(manager.department!.id!)
        .then((res) => {
            setOffers(res.data);
        })
        .catch((err) => {
            console.log(err)
            if (err.request.status === 404) setError(t("offersList.errors.departmentNotFound"));
        })
    }, [manager.department, t]);

    return (
        <Container>
            <h1>{t("managerOffersList.viewTitle")}</h1>
            <OffersList offers={offers} error={error}/>
        </Container>
    );
};

export default ManagerOfferView;