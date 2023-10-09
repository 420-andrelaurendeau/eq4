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
          <Button
            onClick={() => handleClick("/signup/employer")}
            variant="light"
          >
            {t("signup.signup")}
          </Button>
        </Nav>
        {!isConnected() && (
          <Nav>
            <Button onClick={() => handleClick("/users")} variant="light">
              {t("signin")}
            </Button>
          </Nav>
        )}
        {isConnected() && (
          <Nav>
            <LogoutButton />
          </Nav>
        )}
        <LanguageToggler />
      </Navbar.Collapse>
    </Navbar>
  );
}
export default AppHeader;
