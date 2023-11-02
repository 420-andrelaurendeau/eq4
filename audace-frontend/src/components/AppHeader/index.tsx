import { useNavigate } from "react-router-dom";
import { Button, ButtonGroup, Nav, Navbar } from "react-bootstrap";
import LanguageToggler from "../LanguageToggler";
import { useTranslation } from "react-i18next";
import LogoutButton from "../LogoutButton";
import { getAuthorities, isConnected } from "../../services/authService";

function AppHeader() {
  const navigate = useNavigate();
  const { t } = useTranslation();
  const authority = getAuthorities()?.[0]?.toString().toLowerCase();

  const handleClick = (path: string) => {
    navigate(path);
  };

  return (
    <Navbar bg="light" sticky="top" className="px-3 shadow-sm" expand="md">
      <Navbar.Brand href="/">Audace</Navbar.Brand>

      <Navbar.Collapse id="basic-navbar-nav">
        {authority === "employer" && (
          <Nav>
            <Button onClick={() => handleClick(authority + "/offers/new")} variant="light" className="me-2">
              Create Offer
            </Button>
          </Nav>
        )}

        {authority === "manager" && (
          <Nav>
            <Button onClick={() => handleClick(authority + "/offers")} variant="light" className="me-2">
              {t("manager.seeOffersButton")}
            </Button>
          </Nav>
        )}


      </Navbar.Collapse>

      <Nav className="justify-content-end">
        {!isConnected() ? (
          <>
            <ButtonGroup>
              <Button onClick={() => handleClick("/signup/employer")} variant="outline-success" className="me-2">
                {t("signup.signup")}
              </Button>
              <Button onClick={() => handleClick("/login")} variant="outline-primary" className="me-2">
                {t("signin")}
              </Button>
            </ButtonGroup>
          </>
        ) : (
          <Nav>
            <LogoutButton />
          </Nav>
        )}
      </Nav>
      <LanguageToggler />
      {isConnected() && <Navbar.Toggle aria-controls="basic-navbar-nav" />}
    </Navbar>
  );
}

export default AppHeader;