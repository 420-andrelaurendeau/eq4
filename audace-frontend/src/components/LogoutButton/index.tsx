import { Button } from "react-bootstrap";
import { logout } from "../../services/authService";
import { useNavigate } from "react-router-dom";
import { useTranslation } from "react-i18next";

const LogoutButton = () => {
  const navigate = useNavigate();
  const {t} = useTranslation();

  const onClick = () => {
    logout();
    navigate("/");
  };

  return (
    <>
      <Button variant="light" onClick={onClick}>
        {t("logout")}
      </Button>
    </>
  );
};

export default LogoutButton;
