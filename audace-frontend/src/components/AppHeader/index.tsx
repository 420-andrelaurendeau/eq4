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
    <Navbar bg="light" expand="lg" sticky="top" className="mx-3">
      <Navbar.Brand href="/">Audace</Navbar.Brand>
      {/* <Navbar.Toggle aria-controls="basic-navbar-nav" /> */}
      {/* <Navbar.Collapse id="basic-navbar-nav"> */}
          {!isConnected() ? (
            <>
              <Button
                onClick={() => handleClick("/signup/employer")}
                variant="light"
              >
                {t("signup.signup")}
              </Button>
              <Nav>
                <Button onClick={() => handleClick("/login")} variant="light">
                  {t("signin")}
                </Button>
              </Nav>
            </>
          ) : (
            <Nav>
              <LogoutButton />
            </Nav>
          )}
        <Nav className="justify-content-end flex-grow-1">
          <LanguageToggler />
        </Nav>
    </Navbar>
  );
}
export default AppHeader;
