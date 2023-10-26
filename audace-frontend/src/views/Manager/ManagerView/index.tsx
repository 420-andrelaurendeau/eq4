import { Button, Container } from "react-bootstrap";
import { useTranslation } from "react-i18next";
import { useNavigate } from "react-router-dom";
import {useEffect} from "react";

const ManagerView = () => {
  const navigate = useNavigate();
  const { t } = useTranslation();


    useEffect(() => {

    }, []);

  const seeOffers = () => {
    navigate(`/manager/offers`);
  };
  const seeCvs = () => {
    navigate(`/manager/cvs`);
  };

  return (
    <Container>
      <h1>Manager view</h1>
      <Button onClick={seeOffers}>{t("manager.seeOffersButton")}</Button>
      <Button onClick={seeCvs}>{t("manager.seeCvsButton")}</Button>
    </Container>
  );
};

export default ManagerView;
