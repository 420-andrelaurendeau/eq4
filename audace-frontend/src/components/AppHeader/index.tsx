import { useNavigate } from "react-router-dom";
import { Button, Nav, Navbar } from "react-bootstrap";
import LanguageToggler from "../LanguageToggler";
import React from "react";
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
    <Navbar bg="light" expand="lg">
      <Navbar.Brand href="/">Audace</Navbar.Brand>
      <Navbar.Toggle aria-controls="basic-navbar-nav" />
      <Navbar.Collapse id="basic-navbar-nav">
        <Nav>
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
            <>
              <Nav>
                <LogoutButton />
              </Nav>
            </>
          )}
        </Nav>
        <LanguageToggler />
      </Navbar.Collapse>
    </Navbar>
  );
}
export default AppHeader;
