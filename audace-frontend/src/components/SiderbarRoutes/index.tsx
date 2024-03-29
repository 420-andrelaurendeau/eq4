import SignupView from "../../views/Signup";
import { UserType } from "../../model/user";
import LoginView from "../../views/LoginView";
import AuthorizedRoute from "../../components/AuthorizedRoute";
import ConnectedRoute from "../../components/ConnectedRoute";
import PageNotFoundView from "../../views/PageNotFoundView";
import StudentView from "../../views/Student/StudentView";
import ManagerView from "../../views/Manager/ManagerView";
import ManagerOfferView from "../../views/Manager/ManagerOfferView";
import ManagerCvView from "../../views/Manager/ManagerCvView";
import { Authority } from "../../model/auth";
import { getAuthorities, isConnected } from "../../services/authService";
import AddContract from "../../components/AddContract";
import AddOffer from "../../components/AddOffer";
import EditOffer from "../../components/EditOffer";
import EmployerView from "../../views/Employer/EmployerView";
import NotificationSidebar from "../NotificationSidebar";
import { Navigate, Route, Routes } from "react-router-dom";
import { Collapse } from "react-bootstrap";
import SignContract from "../SignContract";

interface Props {
  showNotifications: boolean;
}

const SidebarRoutes = ({ showNotifications }: Props) => {
  return (
    <div className="col d-md-flex">
      <Collapse in={showNotifications}>
        <div id="NotificationSidebarCollapse" className="col-md-3 col-12">
          {isConnected() ? <NotificationSidebar /> : null}
        </div>
      </Collapse>
      <div className="col">
        <Routes>
          <Route
            path="/signup/*"
            element={
              <ConnectedRoute isConnectedRoute={false}>
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
              </ConnectedRoute>
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
                  <Route
                    path="createdContract"
                    element={<ManagerView isContractCreated={true} />}
                  />
                  <Route path="offers" element={<ManagerOfferView />} />
                  <Route path="cvs" element={<ManagerCvView />} />
                  <Route
                    path="contracts/new/:applicationId"
                    element={<AddContract />}
                  />
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
                  <Route path="offers/new" element={<AddOffer />} />
                  <Route path="offers/:id" element={<EditOffer />} />
                  <Route path="*" element={<PageNotFoundView />} />
                </Routes>
              </AuthorizedRoute>
            }
          />
          <Route path="/contract/:id" element={<SignContract />} />
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
      </div>
    </div>
  );
};

export default SidebarRoutes;
