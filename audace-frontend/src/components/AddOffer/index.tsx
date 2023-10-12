import React, { useState } from "react";
import { Button, Form, Row, Col } from "react-bootstrap";
import { useTranslation } from "react-i18next";
import FormInput from "../Signup/FormInput";  

interface OfferFormData {
    title: string;
    description: string;
    internshipStartDate: string;
    internshipEndDate: string;
    offerEndDate: string;
    availablePlaces?: number;
    status?: string; 
    departmentCode: string;
    employerId?: number; 
}


const AddOffer: React.FC = () => {
  const { t } = useTranslation();
  const [title, setTitle] = useState<string>("");
  const [description, setDescription] = useState<string>("");
  const [departmentCode, setDepartmentCode] = useState<string>("");  
  const [internshipStartDate, setInternshipStartDate] = useState<string>("");  
  const [internshipEndDate, setInternshipEndDate] = useState<string>("");  
  const [offerEndDate, setOfferEndDate] = useState<string>("");
  const [availablePlaces, setAvailablePlaces] = useState<number>(3); 
  const [status, setStatus] = useState<string>("PENDING"); 
  const [employerId, setEmployerId] = useState<number>(1);  
  const [errors, setErrors] = useState<string[]>([]);
  
  const handleSubmit = () => {
      if (validateForm()) {
          const formData: OfferFormData = {
              title,
              description,
              departmentCode,
              internshipStartDate,
              internshipEndDate,
              offerEndDate,
              availablePlaces,  
              status,  
              employerId,  
          };
          addOffer(formData);
      }
  };

    const renderDateInputError = (formError: string) => {
      if (errors.includes(formError)) {
          return <Form.Control.Feedback type="invalid">{t(formError)}</Form.Control.Feedback>;
      }
      return null;
  };

    const validateForm = (): boolean => {
        let isValid = true;
        const errorsToDisplay: string[] = [];

        if (!title) errorsToDisplay.push("addOffer.errors.titleRequired");
        if (!description) errorsToDisplay.push("addOffer.errors.descriptionRequired");
        if (!departmentCode) errorsToDisplay.push("addOffer.errors.departmentCodeRequired");
        if (!internshipStartDate) errorsToDisplay.push("addOffer.errors.startDateRequired");
        if (!internshipEndDate) errorsToDisplay.push("addOffer.errors.endDateRequired");
        if (!offerEndDate) errorsToDisplay.push("addOffer.errors.offerEndDateRequired");
        
        setErrors(errorsToDisplay);
        return errorsToDisplay.length === 0;
    };

    const addOffer = async (offerData: OfferFormData) => {
        try {
            const response = await fetch("http://localhost:8080/employers/1/offers", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(offerData),
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

    return (
        <>
            <h3 className="text-center">{t("addOffer.pageTitle")}</h3>
            <Form className="container mt-5">
                <Row>
                    <FormInput 
                        label="addOffer.title"
                        value={title}
                        onChange={(e: any) => setTitle(e.target.value)}
                        errors={errors}
                        formError="addOffer.errors.titleRequired"
                        controlId="formOfferTitle"
                    />

                    <FormInput 
                        label="addOffer.departmentCode"
                        value={departmentCode}
                        onChange={(e: any) => setDepartmentCode(e.target.value)}
                        errors={errors}
                        formError="addOffer.errors.departmentCodeRequired"
                        controlId="formOfferdepartmentCode"
                    />
                </Row>

                <Form.Group controlId="formOfferDescription">
                    <Form.Label>{t("addOffer.description")}</Form.Label>
                    <Form.Control
                        as="textarea"
                        value={description}
                        onChange={(e) => setDescription(e.target.value)}
                    />
                </Form.Group>

                <Row>
            <Col md="4">
                <Form.Group controlId="formOfferStartDate">
                    <Form.Label>{t("addOffer.startDate")}</Form.Label>
                    <Form.Control
                        type="date"
                        value={internshipStartDate}
                        isInvalid={errors.includes("addOffer.errors.startDateRequired")}
                        onChange={(e) => setInternshipStartDate(e.target.value)}
                    />
                    {renderDateInputError("addOffer.errors.startDateRequired")}
                </Form.Group>
            </Col>

            <Col md="4">
                <Form.Group controlId="formOfferEndDate">
                    <Form.Label>{t("addOffer.endDate")}</Form.Label>
                    <Form.Control
                        type="date"
                        value={internshipEndDate}
                        isInvalid={errors.includes("addOffer.errors.endDateRequired")}
                        onChange={(e) => setInternshipEndDate(e.target.value)}
                    />
                    {renderDateInputError("addOffer.errors.endDateRequired")}
                </Form.Group>
            </Col>

            <Col md="4">
                <Form.Group controlId="formOfferOfferEndDate">
                    <Form.Label>{t("addOffer.offerEndDate")}</Form.Label>
                    <Form.Control
                        type="date"
                        value={offerEndDate}
                        isInvalid={errors.includes("addOffer.errors.offerEndDateRequired")}
                        onChange={(e) => setOfferEndDate(e.target.value)}
                    />
                    {renderDateInputError("addOffer.errors.offerEndDateRequired")}
                </Form.Group>
            </Col>
        </Row>

        <Row className="mb-3">
    <Col md="4">
        <Form.Group controlId="formOfferAvailablePlaces">
            <Form.Label>{t("addOffer.availablePlaces")}</Form.Label>
            <Form.Control
                type="number"
                size="sm"  
                min="1"  
                value={availablePlaces}
                isInvalid={errors.includes("addOffer.errors.availablePlacesRequired")}
                onChange={(e) => setAvailablePlaces(Number(e.target.value))}
            />
            {errors.includes("addOffer.errors.availablePlacesRequired") && (
                <Form.Control.Feedback type="invalid">
                    {t("addOffer.errors.availablePlacesRequired")}
                </Form.Control.Feedback>
            )}
        </Form.Group>
    </Col>
</Row>


                <Button variant="primary" className="mt-3" onClick={handleSubmit}>
                    {t("addOffer.submit")}
                </Button>
            </Form>
        </>
    );
};

export default AddOffer;
