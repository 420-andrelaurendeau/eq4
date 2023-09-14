import React from "react";
import logo from "./logo.svg";
import "./App.css";
import "bootstrap/dist/css/bootstrap.min.css";
import { BrowserRouter as Router, Routes, Route, Link } from "react-router-dom";
import { Container } from "react-bootstrap";
import SignupView from "./views/Signup";

function App() {
  return (
    <Router>
      <Container>
        <h1>Audace</h1>
        <Routes>
          <Route
            path="/"
            element={
              <>
                <Link to="/signup">Signup</Link>
              </>
            }
          />
          <Route path="/signup" element={<SignupView />} />
        </Routes>
      </Container>
    </Router>
  );
}

export default App;
