import React from "react";
import "./App.css";
import "bootstrap/dist/css/bootstrap.min.css";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { Navbar } from "react-bootstrap";
import LanguageToggler from "./components/LanguageToggler";
import { useTranslation } from "react-i18next";
import AddInternshipView from "./views/Internship";

function App() {
  const { t } = useTranslation();

  return (
    <>
      <Navbar bg="light" expand="lg">
        <Navbar.Brand href="/">Audace</Navbar.Brand>
        <Navbar.Toggle aria-controls="basic-navbar-nav" />
        <Navbar.Collapse id="basic-navbar-nav">
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
          <Route path="/internship" element={<AddInternshipView />} />
        </Routes>
      </Router>
    </>
  );
}

export default App;
