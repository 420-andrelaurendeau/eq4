import React, { useState } from "react";
import "bootstrap/dist/css/bootstrap.min.css";

const AddInternship: React.FC = () => {
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [department, setDepartment] = useState("");
  const [startDate, setStartDate] = useState("");
  const [endDate, setEndDate] = useState("");
  const [offerEndDate, setOfferEndDate] = useState("");
  const [language, setLanguage] = useState<"english" | "french">("english");

  const handleSubmit = async (event: React.FormEvent) => {
    event.preventDefault();

    const formData = {
      title,
      department,
      description,
      startDate,
      endDate,
      offerEndDate,
    };

    try {
      const response = await fetch("/stages", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(formData),
      });

      if (!response.ok) {
        throw new Error(
          `Backend returned code ${response.status}, body: ${response.statusText}`
        );
      }

      const responseData = await response.json();
      console.log(responseData);
    } catch (error) {
      console.error("There was an error sending the data:", error);
    }
  };

  const toggleLanguage = () => {
    setLanguage((prevLang) => (prevLang === "english" ? "french" : "english"));
  };

  const translations = {
    pageTitle:
      language === "english" ? "Add an Internship" : "Ajouter un Stage",
    title: language === "english" ? "Title" : "Titre",
    department: language === "english" ? "Department" : "Département",
    description: language === "english" ? "Description" : "Description",
    startDate: language === "english" ? "Start Date" : "Date de début",
    endDate: language === "english" ? "End Date" : "Date de fin",
    offerEndDate:
      language === "english" ? "Offer End Date" : "Date de fin d'offre",
    submit: language === "english" ? "Submit" : "Envoyer",
  };

  return (
    <div className="container mt-5">
      <div className="d-flex justify-content-center align-items-center mb-5">
        <h2 className="flex-grow-1 text-center">{translations.pageTitle}</h2>
        <button className="btn btn-secondary" onClick={toggleLanguage}>
          {language === "english" ? "Français" : "English"}
        </button>
      </div>

      <form onSubmit={handleSubmit} className="col-lg-8 mx-auto">
        <div className="mb-2 row">
          <div className="mb-5 col-md-6">
            <label className="form-label">{translations.title}:</label>
            <input
              type="text"
              className="form-control"
              value={title}
              onChange={(e) => setTitle(e.target.value)}
            />
          </div>
          <div className="mb-5 col-md-6">
            <label className="form-label">{translations.department}:</label>
            <input
              type="text"
              className="form-control"
              value={department}
              onChange={(e) => setDepartment(e.target.value)}
            />
          </div>
        </div>
        <div className="mb-5">
          <label className="form-label">{translations.description}:</label>
          <textarea
            className="form-control"
            value={description}
            onChange={(e) => setDescription(e.target.value)}
          />
        </div>
        <div className="mb-5 row">
          <div className="col-md-4">
            <label className="form-label">{translations.startDate}:</label>
            <input
              type="date"
              className="form-control"
              value={startDate}
              onChange={(e) => setStartDate(e.target.value)}
            />
          </div>
          <div className="col-md-4">
            <label className="form-label">{translations.endDate}:</label>
            <input
              type="date"
              className="form-control"
              value={endDate}
              onChange={(e) => setEndDate(e.target.value)}
            />
          </div>
          <div className="mb-5 col-md-4">
            <label className="form-label">{translations.offerEndDate}:</label>
            <input
              type="date"
              className="form-control"
              value={offerEndDate}
              onChange={(e) => setOfferEndDate(e.target.value)}
            />
          </div>
        </div>
        <div className="text-center">
          <button type="submit" className="btn btn-primary">
            {translations.submit}
          </button>
        </div>
      </form>
    </div>
  );
};

export default AddInternship;
