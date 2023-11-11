import { useNavigate } from "react-router-dom";
import { Button, ButtonGroup, Nav, Navbar } from "react-bootstrap";
import LanguageToggler from "../LanguageToggler";
import { useTranslation } from "react-i18next";
import LogoutButton from "../LogoutButton";
import { getAuthorities, getUserId, isConnected } from "../../services/authService";
import { useEffect, useState } from "react";
import { getHasNotificationByUserId } from "../../services/notificationService";
interface Props {
  showNotifications: boolean;
  setShowNotifications: (show: boolean) => void;
}
function AppHeader({showNotifications, setShowNotifications} : Props) {
  const navigate = useNavigate();
  const [newNotifications, setNewNotifications] = useState<boolean>(false);
  const { t } = useTranslation();
  const authority = getAuthorities()?.[0]?.toString().toLowerCase();

  const handleClick = (path: string) => {
    navigate(path);
  };

  useEffect(() => {
    if (!isConnected()) return;
    getHasNotificationByUserId(parseInt(getUserId()!))
      .then((res) => {
        setNewNotifications(res.data);
      })
  });

  return (
    <Navbar bg="light" sticky="top" className="px-3 shadow-sm" expand="md">
      <Navbar.Brand href="/">Audace</Navbar.Brand>
      {isConnected() && (
          <Nav>
            <Button onClick={() => setShowNotifications(!showNotifications)} variant="light">
              {showNotifications ? 
              <div>
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" viewBox="0 0 16 16">
                  <path d="M8 16a2 2 0 0 0 2-2H6a2 2 0 0 0 2 2zm.995-14.901a1 1 0 1 0-1.99 0A5.002 5.002 0 0 0 3 6c0 1.098-.5 6-2 7h14c-1.5-1-2-5.902-2-7 0-2.42-1.72-4.44-4.005-4.901z"/>
                </svg>
              </div>
              :
              <div>
              <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" viewBox="0 0 16 16">
                <path d="M8 16a2 2 0 0 0 2-2H6a2 2 0 0 0 2 2zM8 1.918l-.797.161A4.002 4.002 0 0 0 4 6c0 .628-.134 2.197-.459 3.742-.16.767-.376 1.566-.663 2.258h10.244c-.287-.692-.502-1.49-.663-2.258C12.134 8.197 12 6.628 12 6a4.002 4.002 0 0 0-3.203-3.92L8 1.917zM14.22 12c.223.447.481.801.78 1H1c.299-.199.557-.553.78-1C2.68 10.2 3 6.88 3 6c0-2.42 1.72-4.44 4.005-4.901a1 1 0 1 1 1.99 0A5.002 5.002 0 0 1 13 6c0 .88.32 4.2 1.22 6z"/>
              </svg>
              </div>}
            </Button>
            {newNotifications ?
            <div className="my-auto">NEW</div>
            :
            null}
          </Nav>
        )}

      <Navbar.Collapse id="basic-navbar-nav">
        {authority === "employer" && (
          <Nav>
            <Button onClick={() => handleClick(authority + "/offers/new")} variant="light" className="me-2">
              {t("employer.createOfferButton")}
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
            <LogoutButton setShowNotifications={setShowNotifications}/>
          </Nav>
        )}
      </Nav>
      <LanguageToggler />
      {isConnected() && <Navbar.Toggle aria-controls="basic-navbar-nav" />}
    </Navbar>
  );
}

export default AppHeader;