import React from "react";
import "./App.css";
import "bootstrap/dist/css/bootstrap.min.css";
import {BrowserRouter as Router, Routes, Route} from "react-router-dom";
import SignupView from "./views/Signup";
import { UserType } from "./model/user";
import StudentOfferView from "./views/StudentOfferView";
import ManagerOfferView from "./views/ManagerOfferView";
import ManagerHomePage from "./components/ManagerHomePage";
import AppHeader from "./components/AppHeader";
import UserList from "./components/Login";
import StudentHomePage from "./components/StudentHomePage";
import EmployerHomePage from "./components/EmployerHomePage";

function App() {

  return (
      <Router>
        <>
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
            <Route path="/student/:userId" element={<StudentHomePage />}></Route>
            <Route path="/employer/:userId" element={<EmployerHomePage />}></Route>
            <Route path="/manager/:userId" element={<ManagerHomePage />}></Route>
          </Routes>
      </>
    </Router>
  );
}

export default App;
