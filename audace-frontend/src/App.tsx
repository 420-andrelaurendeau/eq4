import React from "react";
import "./App.css";
import "bootstrap/dist/css/bootstrap.min.css";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import SignupView from "./views/Signup";
import { UserType } from "./model/user";
import StudentOfferView from "./views/StudentOfferView";
import ManagerOfferView from "./views/ManagerOfferView";
import ManagerHomePage from "./components/ManagerHomePage";
import AppHeader from "./components/AppHeader";
import StudentHomePage from "./components/StudentHomePage";
import EmployerHomePage from "./components/EmployerHomePage";
import LoginView from "./views/LoginView";
import AuthorizedRoute from "./components/AuthorizedRoute";
import { Authority } from "./model/auth";

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
          path="/login"
          element={
            <LoginView />
          }
        />
        <Route
          path="/student/*"
          element={
            <AuthorizedRoute requiredAuthority={Authority.STUDENT}>
              <Routes>
                <Route path=":userId" element={<StudentHomePage />} />
                <Route path=":id/offers" element={<StudentOfferView />} />
              </Routes>
            </AuthorizedRoute>
          }
        />
        <Route
          path="/manager/*"
          element={
            <AuthorizedRoute requiredAuthority={Authority.MANAGER}>
              <Routes>
                <Route
                  path="/manager/:userId"
                  element={<ManagerHomePage />}
                />
                <Route path=":id/offers" element={<ManagerOfferView />} />
              </Routes>
            </AuthorizedRoute>
          }
        />
        <Route path="/employer/:userId" element={<EmployerHomePage />} />
      </Routes>
    </Router>
  );
}

export default App;
