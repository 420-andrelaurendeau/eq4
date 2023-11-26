import { useNavigate } from "react-router-dom";
import { Button, ButtonGroup, Nav, Navbar } from "react-bootstrap";
import LanguageToggler from "../LanguageToggler";
import { useTranslation } from "react-i18next";
import LogoutButton from "../LogoutButton";
import {
  getAuthorities,
  getUserId,
  isConnected,
} from "../../services/authService";
import { useEffect, useState } from "react";
import { getHasNotificationByUserId } from "../../services/notificationService";
import "./style.css";
interface Props {
  showNotifications: boolean;
  setShowNotifications: (show: boolean) => void;
}
function AppHeader({ showNotifications, setShowNotifications }: Props) {
  const navigate = useNavigate();
  const [newNotifications, setNewNotifications] = useState<boolean>(false);
  const { t } = useTranslation();
  const authority = getAuthorities()?.[0]?.toString().toLowerCase();

  useEffect(() => {
    if (!isConnected()) return;
    getHasNotificationByUserId(parseInt(getUserId()!)).then((res) => {
      setNewNotifications(res.data);
    });
  });

  return (
    <Navbar bg="light" sticky="top" className="px-3 shadow-sm" expand="md">
      <Navbar.Brand href="/">Audace</Navbar.Brand>
      {isConnected() && (
        <Nav>
          <Button
            onClick={() => setShowNotifications(!showNotifications)}
            variant="light"
            aria-controls="NotificationSidebarCollapse"
            aria-expanded={showNotifications}
          >
            {showNotifications ? (
              <div>
                <i className="bi bi-bell-fill"></i>
              </div>
            ) : (
              <div className="notification-bell">
                <i className="bi bi-bell"></i>
                {newNotifications ? (
                  <div className="my-auto badge">!</div>
                ) : null}
              </div>
            )}
          </Button>
        </Nav>
      )}

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
            <LogoutButton setShowNotifications={setShowNotifications} />
          </Nav>
        )}
      </Nav>
      <LanguageToggler />
      {isConnected() && <Navbar.Toggle aria-controls="basic-navbar-nav" />}
    </Navbar>
  );
}

export default AppHeader;
