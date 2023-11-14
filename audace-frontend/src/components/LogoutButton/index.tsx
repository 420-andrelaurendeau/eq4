import { Button } from "react-bootstrap";
import { logout } from "../../services/authService";
import { useNavigate } from "react-router-dom";
import { useTranslation } from "react-i18next";

interface Props {
  setShowNotifications: (show: boolean) => void;
}

const LogoutButton = ({setShowNotifications} : Props) => {
  const navigate = useNavigate();
  const {t} = useTranslation();

  const onClick = () => {
    setShowNotifications(false);
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
