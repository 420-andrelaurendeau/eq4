import { Button, Container } from "react-bootstrap";
import { useTranslation } from "react-i18next";
import { useNavigate } from "react-router-dom";

const ManagerView = () => {
  const navigate = useNavigate();
  const { t } = useTranslation();

  const seeOffers = () => {
    navigate(`/manager/offers`);
  };

  return (
    <Container>
      <h1>Manager view</h1>
      <Button onClick={seeOffers}>{t("manager.seeOffersButton")}</Button>
    </Container>
  );
};

export default ManagerView;
