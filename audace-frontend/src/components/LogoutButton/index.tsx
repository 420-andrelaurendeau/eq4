import { Button } from "react-bootstrap";
import { logout } from "../../services/authService";
import { useNavigate } from "react-router-dom";

const LogoutButton = () => {
  const navigate = useNavigate();

  const onClick = () => {
    logout();
    navigate("/");
  };

  return (
    <>
      <Button variant="light" onClick={onClick}>
        Logout
      </Button>
    </>
  );
};

export default LogoutButton;
