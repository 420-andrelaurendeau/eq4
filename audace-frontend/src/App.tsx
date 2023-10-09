import React from "react";
import "./App.css";
import "bootstrap/dist/css/bootstrap.min.css";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import SignupView from "./views/Signup";
import { UserType } from "./model/user";
import ManagerOfferView from "./views/ManagerOfferView";
import ManagerHomePage from "./components/ManagerHomePage";
import AppHeader from "./components/AppHeader";
import EmployerHomePage from "./components/EmployerHomePage";
import LoginView from "./views/LoginView";
import AuthorizedRoute from "./components/AuthorizedRoute";
import { Authority } from "./model/auth";
import ConnectedRoute from "./components/ConnectedRoute";
import PageNotFoundView from "./views/PageNotFoundView";
import StudentView from "./views/StudentView";

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
            <ConnectedRoute isConnectedRoute={false}>
              <LoginView />
            </ConnectedRoute>
          }
        />
        <Route
          path="/student/*"
          element={
            <AuthorizedRoute requiredAuthority={Authority.STUDENT}>
              <Routes>
                <Route index element={<StudentView />} />
              </Routes>
            </AuthorizedRoute>
          }
        />
        <Route
          path="/manager/*"
          element={
            <AuthorizedRoute requiredAuthority={Authority.MANAGER}>
              <Routes>
                <Route path="/manager/:userId" element={<ManagerHomePage />} />
                <Route path=":id/offers" element={<ManagerOfferView />} />
              </Routes>
            </AuthorizedRoute>
          }
        />
        <Route path="/employer/:userId" element={<EmployerHomePage />} />
        <Route path="*" element={<PageNotFoundView />} />
      </Routes>
    </Router>
  );
}

export default App;
