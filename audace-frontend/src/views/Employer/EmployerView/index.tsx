import { useEffect, useState } from "react";
import { Container } from "react-bootstrap";
import { useTranslation } from "react-i18next";
import { Offer } from "../../../model/offer";
import { Employer } from "../../../model/user";
import { useNavigate } from "react-router";
import { getUserId } from "../../../services/authService";
import { getEmployerById } from "../../../services/userService";
import { getAllOffersByEmployerIdAndSessionId } from "../../../services/offerService";
import OffersList from "../../../components/OffersList";
import { useSessionContext } from "../../../contextsholders/providers/SessionContextHolder";
import SessionSelector from "../../../components/SessionSelector";
import { useOfferContext } from "../../../contextsholders/providers/OfferContextHolder";

const EmployerView = () => {
  const [employer, setEmployer] = useState<Employer>();
  const [error, setError] = useState<string>("");

  const { t } = useTranslation();
  const navigate = useNavigate();
  const { chosenSession } = useSessionContext();
  const { offers, setOffers } = useOfferContext();

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
        console.log(err);
        if (err.request.status === 404)
          setError(t("employer.errors.employerNotFound"));
      });
  }, [employer, navigate, t]);

  useEffect(() => {
    if (employer === undefined) return;
    if (chosenSession === undefined) return;

    getAllOffersByEmployerIdAndSessionId(employer.id!, chosenSession.id)
      .then((res) => {
        setOffers(res.data);
      })
      .catch((err) => {
        console.log(err);
      });
  }, [employer, chosenSession, setOffers]);

  const updateAvailablePlaces = (offer: Offer) => {
    let updatedOffers = offers.map(o => {
      if (o.id === offer.id) return { ...o, availablePlaces: --o.availablePlaces };
      return o;
    });
    setOffers(updatedOffers);
  };

  return (
    <Container className="mt-3">
      <SessionSelector />
      <OffersList
        offers={offers}
        error={error}
        updateAvailablePlaces={updateAvailablePlaces}
      />
      asd
    </Container>
  );
};

export default EmployerView;