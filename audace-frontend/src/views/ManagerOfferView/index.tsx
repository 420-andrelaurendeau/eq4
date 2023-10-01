import { Container } from "react-bootstrap";
import { Manager, UserType } from "../../model/user";
import { useTranslation } from "react-i18next";
import { useEffect, useState } from "react";
import { Offer } from "../../model/offer";
import OffersList from "../../components/OffersList";
import { getManagerOffersByDepartment } from "../../services/offerService";
import { useParams } from "react-router-dom";
import { getManagerById } from "../../services/userService";

const ManagerOfferView = () => {
    const [manager, setManager] = useState<Manager>();
    const [offers, setOffers] = useState<Offer[]>([]);
    const [error, setError] = useState<string>("");
    const {id} = useParams();
    const {t} = useTranslation();

    useEffect(() => {
        getManagerById(parseInt(id!))
            .then((res) => {
                setManager(res.data);
            })
            .catch((err) => {
                console.log(err)
                if (err.request.status === 404) setError(t("managerOffersList.errors.managerNotFound"));
            })
    }, [manager, id, t]);

    useEffect(() => {
        if (manager === undefined) return;

        getManagerOffersByDepartment(manager.department!.id!)
        .then((res) => {
            setOffers(res.data);
        })
        .catch((err) => {
            console.log(err)
            if (err.request.status === 404) setError(t("offersList.errors.departmentNotFound"));
        })
    }, [manager, t]);

    return (
        <Container>
            <h1>{t("managerOffersList.viewTitle")}</h1>
            <OffersList offers={offers} error={error} userType={UserType.Manager}/>
        </Container>
    );
};

export default ManagerOfferView;