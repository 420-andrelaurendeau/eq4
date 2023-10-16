import React, { useEffect, useState } from "react";
import { Button, Form, Row } from "react-bootstrap";
import { useTranslation } from "react-i18next";
import FormInput from "../Signup/FormInput"; 
import { Offer, OfferStatus } from "../../model/offer";
import { Department } from "../../model/department";

const EditOffer: React.FC = () => {

  const { t } = useTranslation();

  const [title, setTitle] = useState<string>("");
  const [description, setDescription] = useState<string>("");
  const [department, setDepartment] = useState<Department>({id:1, code:"GLO", name:"Genie"} as Department);
  const [internshipStartDate, setInternshipStartDate] = useState<Date>({} as Date);
  const [internshipEndDate, setInternshipEndDate] = useState<Date>({} as Date);
  const [offerEndDate, setOfferEndDate] = useState<Date>({} as Date);
  const [availablePlaces, setAvailablePlaces] = useState<number>(3);
  const [errors, setErrors] = useState<string[]>([]);

  useEffect(() => {
    fetchOffer();
  }, []);

  const fetchOffer = async () => {
    const paths = window.location.pathname.split("/");
    const offerId = paths[paths.length - 1];

    try {
      const response = await fetch(`http://localhost:8080/employers/offers/${offerId}`);

      if (!response.ok) {
        throw new Error(`Backend returned code ${response.status}, body: ${response.statusText}`);
      }

      const offerData: Offer = await response.json();

      setTitle(offerData.title);
      setDescription(offerData.description);
      setDepartment(offerData.department);
      setInternshipStartDate(new Date(offerData.internshipStartDate));
      setInternshipEndDate(new Date(offerData.internshipEndDate));
      setOfferEndDate(new Date(offerData.offerEndDate));

    } catch (error) {
      console.error("There was an error fetching the offer:", error);
    }
  };

  const validateForm = (): boolean => {
    let isValid = true;
    const errorsToDisplay: string[] = [];

    // ... your validation logic here

    setErrors(errorsToDisplay);
    return errorsToDisplay.length === 0;
  };

  const handleSubmit = () => {
    if (validateForm()) {
      // Assuming you might also want to get the offerId from the URL here
      const paths = window.location.pathname.split("/");
      const offerId = paths[paths.length - 1];
      const employerId = paths[paths.length - 3];

      const updatedOffer: Offer = {
        title,
        description,
        department,
        internshipStartDate,
        internshipEndDate,
        offerEndDate,
        availablePlaces,
        id: parseInt (offerId), 
        employerId: (parseInt) (employerId),
        status: {} as OfferStatus
      };
      editOffer(updatedOffer);
    }
  };

  const editOffer = async (updatedOffer: Offer) => {
    try {
      const response = await fetch(`http://localhost:8080/employers/offers/${updatedOffer.id}`, { 
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(updatedOffer),
      });

      if (!response.ok) {
        throw new Error(`Backend returned code ${response.status}, body: ${response.statusText}`);
      }

      const responseData = await response.json();
      console.log(responseData);
    } catch (error) {
      console.error("There was an error sending the data:", error);
    }
  };

  const renderDateInputError = (formError: string) => {
    if (errors.includes(formError)) {
      return <Form.Control.Feedback type="invalid">{t(formError)}</Form.Control.Feedback>;
    }
    return null;
  };

  return (
    <>
      <h3 className="text-center">{t("editOffer.pageTitle")}</h3>
      <Form className="container mt-5">
        <Row>
          <FormInput 
            label="editOffer.title"
            value={title}
            onChange={(e: any) => setTitle(e.target.value)}
            errors={errors}
            formError="editOffer.errors.titleRequired"
            controlId="formOfferTitle"
          />
          <FormInput 
            label="editOffer.department"
            value={department.toString()}
            onChange={(e: any) => setDepartment(e.target.value)}
            errors={errors}
            formError="editOffer.errors.departmentRequired"
            controlId="formOfferDepartment"
          />
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
          {/* Date input components similar to those in AddOffer */}
          {/* ... */}
        </Row>
        <Button variant="primary" className="mt-3" onClick={handleSubmit}>
          {t("editOffer.submit")}
        </Button>
      </Form>
    </>
  );
};

export default EditOffer;
