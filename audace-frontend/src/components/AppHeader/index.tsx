import {useNavigate} from "react-router-dom";
import {Button, Nav, Navbar} from "react-bootstrap";
import LanguageToggler from "../LanguageToggler";
import React from "react";
import {useTranslation} from "react-i18next";

function AppHeader() {
    const navigate = useNavigate();
    const { t } = useTranslation();

    const handleClick = (path : string) => {
        navigate(path);
    };

    return (
        <Navbar bg="light" expand="lg">
            <Navbar.Brand href="/">Audace</Navbar.Brand>
            <Navbar.Toggle aria-controls="basic-navbar-nav" />
            <Navbar.Collapse id="basic-navbar-nav">
                <Nav>
                    <Button onClick={() => handleClick("/signup/employer")} variant="light">{t("signup.signup")}</Button>
                </Nav>
                <Nav style={{ paddingRight: '1rem' }}>
                    <Button onClick={() => handleClick("/users")} variant="light">Sign In</Button>
                </Nav>
                <LanguageToggler />
            </Navbar.Collapse>
        </Navbar>
    )
}
export default AppHeader;