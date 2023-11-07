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

  return (
    <Navbar bg="light" sticky="top" className="px-3 shadow-sm" expand="md">
      <Navbar.Brand href="/">Audace</Navbar.Brand>

      <Navbar.Collapse id="basic-navbar-nav">
        {authority === "employer" && (
          <Nav>
            <Button
              onClick={() => navigate(authority + "/offers/new")}
              variant="light"
              className="me-2"
            >
              {t("employer.addOfferButton")}
            </Button>
          </Nav>
        )}

        {authority === "manager" && (
          <Nav>
            <Button
              onClick={() => navigate(authority + "/offers")}
              variant="light"
              className="me-2"
            >
              {t("manager.seeOffersButton")}
            </Button>
          </Nav>
        )}
      </Navbar.Collapse>

      <Nav className="justify-content-end">
        {!isConnected() ? (
          <>
            <ButtonGroup>
              <Button
                onClick={() => navigate("/signup/employer")}
                variant="outline-success"
                className="me-2"
              >
                {t("signup.signup")}
              </Button>
              <Button
                onClick={() => navigate("/login")}
                variant="outline-primary"
                className="me-2"
              >
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
