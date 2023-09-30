import { Container } from "react-bootstrap";
import { Manager, UserType } from "../../model/user";
import { Department } from "../../model/department";
import { useTranslation } from "react-i18next";
import { useEffect, useState } from "react";
import { Offer } from "../../model/offer";
import OffersList from "../../components/OffersList";
import { getManagerOffersByDepartment } from "../../services/offerService";

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
    type: "manager"
}

interface Props {
    manager: Manager;
}

const ManagerOfferView = ({manager}: Props) => {
    const [offers, setOffers] = useState<Offer[]>([]);
    const [error, setError] = useState<string>("");
    const {t} = useTranslation();

    useEffect(() => {
        getManagerOffersByDepartment(manager.department!.id!)
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
            <OffersList offers={offers} error={error} userType={UserType.Manager}/>
        </Container>
    );
};

export default ManagerOfferView;