import { useEffect, useState } from "react";
import { Container } from "react-bootstrap";
import { useTranslation } from "react-i18next";
import { Offer } from "../../../model/offer";
import { Employer, UserType } from "../../../model/user";
import { useNavigate } from "react-router";
import { getUserId } from "../../../services/authService";
import { getEmployerById } from "../../../services/userService";
import { getAllOffersByEmployerId } from "../../../services/offerService";
import OffersList from "../../../components/OffersList";
import { useSessionContext } from "../../../contextsholders/providers/SessionContextHolder";
import SessionSelector from "../../../components/SessionSelector";
import Applications from "../../../components/Applications";


const EmployerView = () => {
  const [employer, setEmployer] = useState<Employer>();
  const [offers, setOffers] = useState<Offer[]>([]);
  const [error, setError] = useState<string>("");
  const [offerApplication, setOfferApplication] = useState<Offer>();
  const {t} = useTranslation();
  const navigate = useNavigate();
  const {chosenSession} = useSessionContext();

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

    getAllOffersByEmployerId(employer.id!, chosenSession.id)
      .then((res) => {
        setOffers(res.data);
      })
      .catch((err) => {
        console.log(err);
      });
  }, [employer, chosenSession]);

  const seeApplications = (offer: Offer) => {
      setOfferApplication(offer);
  }

  return (
    <Container className="mt-3">
      <SessionSelector />
      <OffersList offers={offers} error={error} userType={UserType.Employer} seeApplications={seeApplications}/>
      {offerApplication !== undefined && <Applications offer={offerApplication}/>}
    </Container>
  );
};
export default EmployerView;