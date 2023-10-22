import { Button } from "react-bootstrap";
import { logout } from "../../services/authService";
import { useNavigate } from "react-router-dom";
import { useTranslation } from "react-i18next";

const LogoutButton = () => {
  const navigate = useNavigate();
  const {t} = useTranslation();

  const onClick = () => {
    logout();
    navigate("/login");
  };

  return (
    <>
      <Button variant="outline-danger" onClick={onClick} className="me-2">
        {t("logout")}
      </Button>
    </>
  );
};

export default LogoutButton;
