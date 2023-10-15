import { useNavigate } from "react-router-dom";
import { Button, Nav, Navbar } from "react-bootstrap";
import LanguageToggler from "../LanguageToggler";
import { useTranslation } from "react-i18next";
import LogoutButton from "../LogoutButton";
import { isConnected } from "../../services/authService";

function AppHeader() {
  const navigate = useNavigate();
  const { t } = useTranslation();

  const handleClick = (path: string) => {
    navigate(path);
  };

  return (
    <Navbar bg="light" sticky="top" className="px-3 shadow-sm">
      <Navbar.Brand href="/">Audace</Navbar.Brand>
      <Nav className="col justify-content-end">
          {!isConnected() ? (
            <>
              <Button onClick={() => handleClick("/signup/employer")} variant="outline-success" className="me-2">
                {t("signup.signup")}
              </Button>
              <Button onClick={() => handleClick("/login")} variant="outline-primary" className="me-2">
                {t("signin")}
              </Button>
            </>
          ) : (
            <Nav>
              <LogoutButton />
            </Nav>
          )}
        <LanguageToggler />
      </Nav>
    </Navbar>
  );
  
}
export default AppHeader;
