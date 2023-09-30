import React from "react";
import "./App.css";
import "bootstrap/dist/css/bootstrap.min.css";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { Nav, Navbar } from "react-bootstrap";
import LanguageToggler from "./components/LanguageToggler";
import { useTranslation } from "react-i18next";
import SignupView from "./views/Signup";
import { UserType } from "./model/user";
import StudentOfferView from "./views/StudentOfferView";
import UserList from "./components/Login/UserList";
import StudentHomePage from "./components/StudentHomePage";
import EmployerHomePage from "./components/EmployerHomePage";
import ManagerOfferView from "./views/ManagerOfferView";

function App() {
  const { t } = useTranslation();

  return (
    <>
      <Navbar bg="light" expand="lg">
        <Navbar.Brand href="/">Audace</Navbar.Brand>
        <Navbar.Toggle aria-controls="basic-navbar-nav" />
        <Navbar.Collapse id="basic-navbar-nav">
          <Nav>
            <Nav.Link href="/signup/employer">{t("signup.signup")}</Nav.Link>
          </Nav>
          <LanguageToggler />
        </Navbar.Collapse>
      </Navbar>
      <Router>
        <Routes>
          <Route
            path="/"
            element={
              <>
                <h1>OSE ÊTRE MEILLEUR</h1>
              </>
            }
          />
          <Route path="/student/*" element={
            <Routes>
              <Route path="offers" element={<StudentOfferView student={tempStudent} />}/>
            </Routes>
          } />
          <Route path="/manager/*" element={
            <Routes>
              <Route path="offers" element={<ManagerOfferView manager={tempManager} />}/>
            </Routes>
          } />
          <Route path="/signup/*" element={
            <Routes>
              <Route path="employer" element={<SignupView userType={UserType.Employer} />}/>
              <Route path="student/:depCode" element={<SignupView userType={UserType.Student} />}/>
            </Routes>
          }>
          </Route>
          <Route path="/users/*" element={
            <Routes>
              <Route path="" element={<UserList></UserList>}/>
            </Routes>
          }>
          </Route>
          <Route path="/student/:userId" element={<StudentHomePage></StudentHomePage>}></Route>
          <Route path="/employer/:userId" element={<EmployerHomePage></EmployerHomePage>}></Route>
        </Routes>
      </Router>
    </>
  );
}

export default App;
