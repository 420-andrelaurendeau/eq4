import { Container } from "react-bootstrap";
import { Employer, UserType } from "../../../model/user";
import { useTranslation } from "react-i18next";
import { useEffect, useState } from "react";
import { Offer } from "../../../model/offer";
import { useParams } from "react-router-dom";
import { getEmployerById } from "../../../services/userService";
import { getAllOffersByEmployerId } from "../../../services/offerService";
import OffersList from "../../../components/OffersList";
import { useSessionContext } from "../../../contextsholders/providers/SessionContextHolder";

const EmployerOfferView = () => {
  const [employer, setEmployer] = useState<Employer>();
  const { id } = useParams();
  const [offers, setOffers] = useState<Offer[]>([]);
  const [error, setError] = useState<string>("");
  const { t } = useTranslation();
  const { currentSession } = useSessionContext();

  useEffect(() => {
    getEmployerById(parseInt(id!))
      .then((res) => {
        setEmployer(res.data);
      })
      .catch((err) => {
        console.log(err);
        if (err.request.status === 404)
          setError(t("employer.errors.employerNotFound"));
      });
  }, [employer, id, t]);

  useEffect(() => {
    if (employer === undefined) return;
    if (currentSession === undefined) return;

    getAllOffersByEmployerId(employer.id!, currentSession.id)
      .then((res) => {
        setOffers(res.data);
      })
      .catch((err) => {
        console.log(err);
      });
  }, [employer, t, currentSession]);

  return (
    <Container>
      <OffersList offers={offers} error={error} userType={UserType.Employer} />
    </Container>
  );
};

export default EmployerOfferView;
