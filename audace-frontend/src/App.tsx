import React from 'react';
import logo from './logo.svg';
import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import PublishedOffers from "./components/Employer/PublishedOffers";

function App() {
  return (
    <div>
      <Router>
        <Routes>
          <Route path="/employer/offers/:id" element={<PublishedOffers/>}/>
        </Routes>
      </Router>
    </div>
  );
}

export default App;
