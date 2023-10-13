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
import AppHeader from "./components/AppHeader";
import LoginView from "./views/LoginView";
import AuthorizedRoute from "./components/AuthorizedRoute";
import { Authority } from "./model/auth";
import ConnectedRoute from "./components/ConnectedRoute";
import PageNotFoundView from "./views/PageNotFoundView";
import StudentView from "./views/StudentView";
import ManagerView from "./views/ManagerView";
import ManagerOfferView from "./views/ManagerOfferView";
import EmployerView from "./views/EmployerView";

function App() {
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
function App() {
    return (
        <Router>
            <AppHeader />
            <Routes>
                <Route
                    path="/"
                    element={
                        <>
                            <h1>OSE ÊTRE MEILLEUR</h1>
                        </>
                    }
                />
                <Route
                    path="/signup/*"
                    element={
                        <Routes>
                            <Route
                                path="employer"
                                element={<SignupView userType={UserType.Employer} />}
                            />
                            <Route
                                path="student/:depCode"
                                element={<SignupView userType={UserType.Student} />}
                            />
                        </Routes>
                    }
                />
                <Route
                    path="/login/*"
                    element={
                        <ConnectedRoute isConnectedRoute={false}>
                            <Routes>
                                <Route index element={<LoginView />} />
                                <Route path="disconnected" element={<LoginView />} />
                                <Route path="*" element={<PageNotFoundView />} />
                            </Routes>
                        </ConnectedRoute>
                    }
                />
                <Route
                    path="/student/*"
                    element={
                        <AuthorizedRoute requiredAuthority={Authority.STUDENT}>
                            <Routes>
                                <Route index element={<StudentView />} />
                                <Route path="*" element={<PageNotFoundView />} />
                            </Routes>
                        </AuthorizedRoute>
                    }
                />
                <Route
                    path="/manager/*"
                    element={
                        <AuthorizedRoute requiredAuthority={Authority.MANAGER}>
                            <Routes>
                                <Route index element={<ManagerView />} />
                                <Route path="offers" element={<ManagerOfferView />} />
                                <Route path="*" element={<PageNotFoundView />} />
                            </Routes>
                        </AuthorizedRoute>
                    }
                />
                <Route
                    path="/employer/*"
                    element={
                        <AuthorizedRoute requiredAuthority={Authority.EMPLOYER}>
                            <Routes>
                                <Route index element={<EmployerView />} />
                                <Route path="*" element={<PageNotFoundView />} />
                            </Routes>
                        </AuthorizedRoute>
                    }
                />
                <Route path="*" element={<PageNotFoundView />} />
            </Routes>
        </Router>
    );
}
export default App;
