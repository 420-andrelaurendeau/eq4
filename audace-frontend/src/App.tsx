import React from "react";
import "./App.css";
import "bootstrap/dist/css/bootstrap.min.css";
import {
  BrowserRouter as Router,
  Routes,
  Route,
  Navigate,
} from "react-router-dom";
import SignupView from "./views/Signup";
import { UserType } from "./model/user";
import AppHeader from "./components/AppHeader";
import LoginView from "./views/LoginView";
import AuthorizedRoute from "./components/AuthorizedRoute";
import ConnectedRoute from "./components/ConnectedRoute";
import PageNotFoundView from "./views/PageNotFoundView";
import StudentView from "./views/Student/StudentView";
import ManagerView from "./views/Manager/ManagerView";
import ManagerOfferView from "./views/Manager/ManagerOfferView";
import EmployerView from "./views/Employer/EmployerView";
import ManagerCvView from "./views/Manager/ManagerCvView";
import { Authority } from "./model/auth";
import { getAuthorities } from "./services/authService";
import EmployerApplicationView from "./views/Employer/EmployerApplicationView";
import ProviderWrapper from "./contextsholders/providers/ProviderWrapper";

function App() {
  return (
    <ProviderWrapper>
      <Router>
        <AppHeader />
        <Routes>
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
                  <Route path="createdUser" element={<LoginView />} />
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
                  <Route
                    path="offers"
                    element={<StudentView viewUpload={false} />}
                  />
                  <Route
                    path="upload"
                    element={<StudentView viewOffers={false} />}
                  />
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
                  <Route path="cvs" element={<ManagerCvView />} />
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
                  <Route path="applications" element={<EmployerApplicationView />} />
                  <Route path="*" element={<PageNotFoundView />} />
                </Routes>
              </AuthorizedRoute>
            }
          />
          <Route path="*" element={<PageNotFoundView />} />
          <Route
            path="/"
            element={
              <Navigate
                to={getAuthorities()?.[0]?.toString().toLowerCase() || "/login"}
              />
            }
          />
        </Routes>
      </Router>
    </ProviderWrapper>
  );
}

export default App;
