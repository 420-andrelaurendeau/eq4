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
import StudentHomePage from "./components/StudentHomePage";
import EmployerHomePage from "./components/EmployerHomePage";
import UserList from "./components/Login";
import ManagerOfferView from "./views/ManagerOfferView";
import ManagerHomePage from "./components/ManagerHomePage";
import EmployerOfferView from "./views/EmployerOfferView";

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
              <Route path=":id/offers" element={<StudentOfferView />}/>
            </Routes>
          } />
          <Route path="/manager/*" element={
            <Routes>
              <Route path=":id/offers" element={<ManagerOfferView />}/>
            </Routes>
          } />
          <Route path="/employer/*" element={
            <Routes>
              <Route path=":id/offers" element={<EmployerOfferView/>}/>
            </Routes>
          }/>
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
          <Route path="/employer/:userId" element={<EmployerHomePage></EmployerHomePage>}>

          </Route>
          <Route path="/manager/:userId" element={<ManagerHomePage />}></Route>
        </Routes>
      </Router>
    </>
  );
}

export default App;
