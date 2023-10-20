import React, { useEffect, useState } from "react";
import { Button, Form, Row, Col } from "react-bootstrap";
import { useTranslation } from "react-i18next";
import FormInput from "../Signup/FormInput";
import { Offer, OfferStatus } from "../../model/offer";
import { Department } from "../../model/department";
import { useNavigate } from 'react-router-dom';

interface OfferFormData {
  id: number,
  title: string,
  description: string,
  departmentCode: string,
  internshipStartDate:  string,
  internshipEndDate:  string,
  offerEndDate:  string,
  availablePlaces: number,
  status: string,
  employerId: number
  };


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
  const [departments, setDepartments] = useState<Department[]>([]);

  const navigate = useNavigate();

  useEffect(() => {
    fetchOffer();
    fetchDepartments();
  }, []);

  const fetchDepartments = async () => {
    try {
      const response = await fetch("http://localhost:8080/employers/departments");

      if (!response.ok) {
        throw new Error(
          `Backend returned code ${response.status}, body: ${response.statusText}`
        );
      }

      const departmentData: Department[] = await response.json();
      setDepartments(departmentData);
      setDepartment(departmentData[0]);
    } catch (error) {
      console.error("There was an error fetching the departments:", error);
    }
  };

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
      setDepartment(offerData.department || {id:1, code:"GLO", name:"Genie"} as Department);
      setInternshipStartDate(new Date(offerData.internshipStartDate));
      setInternshipEndDate(new Date(offerData.internshipEndDate));
      setOfferEndDate(new Date(offerData.offerEndDate));

    } catch (error) {
      console.error("There was an error fetching the offer:", error);
    }
  };

  const formatDateForInput = (date: Date | null): string => {
    if (date instanceof Date) {
        return date.toISOString().substring(0, 10);
    }
    return "";
};

  function isValidDate(d: any) {
    return d instanceof Date && !isNaN(d.getTime());
  }

  const validateForm = (): boolean => {
    let isValid = true;
    const errorsToDisplay: string[] = [];

    if (!title) errorsToDisplay.push("addOffer.errors.titleRequired");
    if (!description) errorsToDisplay.push("addOffer.errors.descriptionRequired");
    if (!department) errorsToDisplay.push("addOffer.errors.departmentCodeRequired");

    if (!isValidDate(internshipStartDate)) errorsToDisplay.push("addOffer.errors.invalidStartDate");
    if (!isValidDate(internshipEndDate)) errorsToDisplay.push("addOffer.errors.invalidEndDate");
    if (!isValidDate(offerEndDate)) errorsToDisplay.push("addOffer.errors.invalidOfferEndDate");
  

    setErrors(errorsToDisplay);
    return errorsToDisplay.length === 0;
  };

  const handleSubmit = () => {
    console.log("Submit button clicked");
    if (validateForm()) {
      const paths = window.location.pathname.split("/");
      const offerId = paths[paths.length - 1];
      const employerId = paths[paths.length - 3];

      const updatedOffer: OfferFormData = {
        id: parseInt(offerId),
        title,
        description,
        departmentCode: department.code,
        internshipStartDate: formatDateForInput(internshipStartDate),
        internshipEndDate: formatDateForInput(internshipEndDate),
        offerEndDate: formatDateForInput(offerEndDate),
        availablePlaces,
        employerId: parseInt(employerId),
        status: OfferStatus.PENDING
      };
      editOffer(updatedOffer, parseInt(offerId));
      navigate(`/employer/${employerId}/offers`);
    }
  };

  const editOffer = async (updatedOffer: OfferFormData, id: number) => {
    try {
      console.log("Offer to update:", updatedOffer);
      const response = await fetch(`http://localhost:8080/employers/offers`, {
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
            formError="error.title.required" controlId={""}          />
        </Row>
        <Row>
          <FormInput
            label="editOffer.description"
            value={description}
            onChange={(e: any) => setDescription(e.target.value)}
            errors={errors}
            formError="error.description.required" controlId={""}          />
        </Row>
        <Row>
          <Col>
            <Form.Group controlId="formDepartment">
              <Form.Label>{t("addOffer.department")}</Form.Label>
              <Form.Control as="select" value={department?.id || ""} onChange={(e: any) => setDepartment(departments.find(d => d.id === parseInt(e.target.value)) || {id: 1, name: "Genie Logiciel", code: "GLO"} as Department)}>
                {departments.map((dept: Department) => (
                  <option key={dept.id} value={dept.id}>
                    {dept.name}
                  </option>
                ))}
              </Form.Control>
            </Form.Group>
          </Col>
        </Row>
        <Row>
        <Col md="4">
          <Form.Group controlId="formOfferStartDate">
            <Form.Label>{t("addOffer.startDate")}</Form.Label>
            <Form.Control
              type="date"
              value={formatDateForInput(internshipStartDate)}
              isInvalid={errors.includes("addOffer.errors.startDateRequired")}
              onChange={(e) => {
                const newDate = new Date(e.target.value);
                if (isValidDate(newDate)) {
                  setInternshipStartDate(newDate);
                } else {
                  setErrors((prevErrors) => [...prevErrors, "addOffer.errors.invalidDate"]);
                }
              }}
              
            />
            {renderDateInputError("addOffer.errors.startDateRequired")}
          </Form.Group>
        </Col>

        <Col md="4">
          <Form.Group controlId="formOfferEndDate">
            <Form.Label>{t("addOffer.endDate")}</Form.Label>
            <Form.Control
              type="date"
              value={formatDateForInput(internshipEndDate)}
              isInvalid={errors.includes("addOffer.errors.endDateRequired")}
              onChange={(e) => {
                const newDate = new Date(e.target.value);
                if (isValidDate(newDate)) {
                  setInternshipEndDate(newDate);
                } else {
                  setErrors((prevErrors) => [...prevErrors, "addOffer.errors.invalidDate"]);
                }
              }}
              
            />
            {renderDateInputError("addOffer.errors.endDateRequired")}
          </Form.Group>
        </Col>

        <Col md="4">
          <Form.Group controlId="formOfferOfferEndDate">
            <Form.Label>{t("addOffer.offerEndDate")}</Form.Label>
            <Form.Control
              type="date"
              value={formatDateForInput(offerEndDate)}
              isInvalid={errors.includes("addOffer.errors.offerEndDateRequired")}
              onChange={(e) => {
                const newDate = new Date(e.target.value);
                if (isValidDate(newDate)) {
                  setOfferEndDate(newDate);
                } else {
                  setErrors((prevErrors) => [...prevErrors, "addOffer.errors.invalidDate"]);
                }
              }}
              
            />
            {renderDateInputError("addOffer.errors.offerEndDateRequired")}
          </Form.Group>
        </Col>
      </Row>
        <Row>
          <Col>
            <Form.Group controlId="formAvailablePlaces">
              <Form.Label>{t("addOffer.availablePlaces")}</Form.Label>
              <Form.Control type="number" value={availablePlaces} onChange={(e: any) => setAvailablePlaces(e.target.value)}/>
            </Form.Group>
          </Col>
        </Row>
        <Row>
          <Col className="mt-3">
            <Button variant="primary" onClick={handleSubmit}>{t("editOffer.submit")}</Button>
          </Col>
        </Row>
      </Form>
    </>
  );
};

export default EditOffer;
