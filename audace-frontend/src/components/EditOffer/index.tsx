import React, { useState } from "react";
import { Button, Form, Row, Col } from "react-bootstrap";
import { useTranslation } from "react-i18next";

const EditOffer: React.FC = () => {
  const { t } = useTranslation();
  const [title, setTitle] = useState<string>("");
  const [description, setDescription] = useState<string>("");
  const [department, setDepartment] = useState<string>("");
  const [startDate, setStartDate] = useState<string>("");
  const [endDate, setEndDate] = useState<string>("");
  const [offerEndDate, setOfferEndDate] = useState<string>("");
  const [personWhoPosted, setPersonWhoPosted] = useState<string>("");
  const [organisation, setOrganisation] = useState<string>("");

  const validateForm = () => {
    if (
      !title ||
      !description ||
      !department ||
      !startDate ||
      !endDate ||
      !offerEndDate
    ) {
      alert(t("editOffer.validationError"));
      return false;
    }
    return true;
  };

  const editOffer = async (Offer: any) => {
    try {
      const response = await fetch("/offers", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(Offer),
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

  const handleSubmit = () => {
    if (!validateForm()) return;

    const formData = {
      title,
      personWhoPosted,
      organisation,
      description,
      department,
      startDate,
      endDate,
      offerEndDate,
    };

    editOffer(formData);
  };

  return (
    <>
      <h3 className="text-center">{t("editOffer.pageTitle")}</h3>
      <Form className="container mt-5">
        <Row>
          <Form.Group as={Col} md="6" controlId="formOfferTitle">
            <Form.Label>{t("editOffer.title")}</Form.Label>
            <Form.Control
              type="text"
              value={title}
              onChange={(e) => setTitle(e.target.value)}
            />
          </Form.Group>

          <Form.Group as={Col} md="6" controlId="formOfferDepartment">
            <Form.Label>{t("editOffer.department")}</Form.Label>
            <Form.Control
              type="text"
              value={department}
              onChange={(e) => setDepartment(e.target.value)}
            />
          </Form.Group>
        </Row>

        <Form.Group controlId="formOfferDescription">
          <Form.Label>{t("editOffer.description")}</Form.Label>
          <Form.Control
            as="textarea"
            value={description}
            onChange={(e) => setDescription(e.target.value)}
          />
        </Form.Group>

        <Row>
          <Form.Group as={Col} md="4" controlId="formOfferStartDate">
            <Form.Label>{t("editOffer.startDate")}</Form.Label>
            <Form.Control
              type="date"
              value={startDate}
              onChange={(e) => setStartDate(e.target.value)}
            />
          </Form.Group>

          <Form.Group as={Col} md="4" controlId="formOfferEndDate">
            <Form.Label>{t("editOffer.endDate")}</Form.Label>
            <Form.Control
              type="date"
              value={endDate}
              onChange={(e) => setEndDate(e.target.value)}
            />
          </Form.Group>

          <Form.Group as={Col} md="4" controlId="formOfferOfferEndDate">
            <Form.Label>{t("editOffer.offerEndDate")}</Form.Label>
            <Form.Control
              type="date"
              value={offerEndDate}
              onChange={(e) => setOfferEndDate(e.target.value)}
            />
          </Form.Group>
        </Row>

        <Button variant="primary" className="mt-3" onClick={handleSubmit}>
          {t("editOffer.submit")}
        </Button>
      </Form>
    </>
  );
};

export default EditOffer;