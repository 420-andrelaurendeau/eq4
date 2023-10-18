import { Container } from "react-bootstrap";
import { Manager, UserType } from "../../model/user";
import { useTranslation } from "react-i18next";
import { useEffect, useState } from "react";
import { Offer, OfferStatus } from "../../model/offer";
import OffersList from "../../components/OffersList";
import { getManagerOffersByDepartment } from "../../services/offerService";
import { getManagerById } from "../../services/userService";
import { getUserId } from "../../services/authService";
import { useNavigate } from "react-router-dom";

const ManagerOfferView = () => {
    const [manager, setManager] = useState<Manager>();
    const [offers, setOffers] = useState<Offer[]>([]);
    const [offersAccepted, setOffersAccepted] = useState<Offer[]>([]);
    const [offersRefused, setOffersRefused] = useState<Offer[]>([]);
    const [error, setError] = useState<string>("");
    const {t} = useTranslation();
    const navigate = useNavigate();

    useEffect(() => {
        const id = getUserId();

        if (id == null) {
            navigate("/pageNotFound");
            return;
        }

    getManagerById(parseInt(id!))
      .then((res) => {
        setManager(res.data);
      })
      .catch((err) => {
        console.log(err);
        if (err.request && err.request.status === 404)
          setError(t("managerOffersList.errors.managerNotFound"));
      });
  }, [manager, t, navigate]);

    useEffect(() => {
        if (manager === undefined) return;

    getManagerOffersByDepartment(manager.department!.id!)
      .then((res) => {
        let acceptedOffers = [];
        let refusedOffers = [];
        let offers = [];
        for (let i = 0; i < res.data.length; i = i + 1) {
          if (res.data[i].status === "ACCEPTED") {
            acceptedOffers.push(res.data[i]);
          } else if (res.data[i].status === "REFUSED") {
            refusedOffers.push(res.data[i]);
          } else if (res.data[i].status === "PENDING") {
            offers.push(res.data[i]);
          }
        }
        setOffersAccepted(acceptedOffers);
        setOffersRefused(refusedOffers);
        setOffers(offers);
      })
      .catch((err) => {
        console.log(err);
        if (err.request && err.request.status === 404)
          setError(t("offersList.errors.departmentNotFound"));
      });
  }, [manager, t]);

    const updateOffersState = (offer: Offer, offerStatus: OfferStatus) => {
        let newOffers = offers.filter((o) => o.id !== offer.id);
        offer.status = offerStatus;
        setOffers(newOffers);
        if (offerStatus === "ACCEPTED") {
            setOffersAccepted([...offersAccepted, offer]);
        } else if (offerStatus === "REFUSED") {
            setOffersRefused([...offersRefused, offer]);
        }
    };
    return (
        <Container>
            <h1>{t("managerOffersList.viewTitle")}</h1>
            {offers.length > 0 ? (
                <OffersList
                    offers={offers}
                    error={error}
                    userType={UserType.Manager}
                    updateOffersState={updateOffersState}
                />
            ) : (
                <p>{t("managerOffersList.noMorePendingOffers")}</p>
            )}
            {offersAccepted.length > 0 ? (
                <OffersList
                    offers={offersAccepted}
                    error={error}
                    userType={UserType.Manager}
                />
            ) : null}
            {offersRefused.length > 0 ? (
                <OffersList
                    offers={offersRefused}
                    error={error}
                    userType={UserType.Manager}
                />
            ) : null}
        </Container>
    );
};

export default ManagerOfferView;