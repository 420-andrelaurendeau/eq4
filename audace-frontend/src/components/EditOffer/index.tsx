import React, { useEffect, useState } from "react";
import { Alert, Button, Form, Row, Col } from "react-bootstrap";
import { useTranslation } from "react-i18next";
import { Offer, OfferStatus } from "../../model/offer";
import { Department } from "../../model/department";
import { Employer } from "../../model/user";
import { useNavigate } from 'react-router-dom';
import http from "../../constants/http";
import { getAllDepartments } from "../../services/departmentService";
import { getEmployersOfferById } from "../../services/offerService";
import FormInput from "../FormInput";



const EditOffer: React.FC = () => {
  const { t } = useTranslation();

  const [title, setTitle] = useState<string>("");
  const [description, setDescription] = useState<string>("");
  const [department, setDepartment] = useState<Department>({ id: 1, code: "GLO", name: "Genie" } as Department);
  const [internshipStartDate, setInternshipStartDate] = useState<Date>({} as Date);
  const [internshipEndDate, setInternshipEndDate] = useState<Date>({} as Date);
  const [offerEndDate, setOfferEndDate] = useState<Date>({} as Date);
  const [availablePlaces, setAvailablePlaces] = useState<number>(3);
  const [errors, setErrors] = useState<string[]>([]);
  const [departments, setDepartments] = useState<Department[]>([]);
  const [employer, setEmployer] = useState<Employer>({} as Employer);
  const [status, setStatus] = useState<OfferStatus>(OfferStatus.PENDING);

  const navigate = useNavigate();

  useEffect(() => {
    getAllDepartments()
      .then((res) => {
        setDepartments(res.data);
        setDepartment(res.data[0]);
      })
      .catch((error) => {
        console.error("There was an error fetching the departments:", error);
      });

    const paths = window.location.pathname.split("/");
    const offerId = paths[paths.length - 1];

    getEmployersOfferById(parseInt(offerId))
      .then((res) => {
        const offerData: Offer = res.data;

        setTitle(offerData.title);
        setDescription(offerData.description);
        setDepartment(offerData.department || { id: 1, code: "GLO", name: "Genie" } as Department);
        setInternshipStartDate(new Date(offerData.internshipStartDate));
        setInternshipEndDate(new Date(offerData.internshipEndDate));
        setOfferEndDate(new Date(offerData.offerEndDate));
        setEmployer(offerData.employer);
      })
      .catch((error) => {
        console.error("There was an error fetching the offer:", error);
      });
  }, []);



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
    if (internshipEndDate <= internshipStartDate) errorsToDisplay.push("addOffer.errors.endDateBeforeStartDate");
    
    setErrors(errorsToDisplay);
    return errorsToDisplay.length === 0;
  };

  const handleDateChange = (setter: React.Dispatch<React.SetStateAction<Date>>, value: string) => {
    const newDate = new Date(value);
    if (isValidDate(newDate)) {
      setter(newDate);
    }
  };

  const handleSubmit = () => {
    if (validateForm()) {
      const paths = window.location.pathname.split("/");
      const offerId = paths[paths.length - 1];
      const employerId = paths[paths.length - 3];

      const updatedOffer: Offer = {
        id: parseInt(offerId),
        title,
        description,
        department,
        internshipStartDate,
        internshipEndDate,
        offerEndDate,
        availablePlaces,
        employer,
        offerStatus: status as OfferStatus,
      };
      editOffer(updatedOffer, parseInt(offerId));
      navigate(`/employer`);
    }
  };

  const editOffer = async (updatedOffer: Offer, id: number) => {
    try {
      const response = await http.put(`/employers/${employer.id}/offers`, updatedOffer);

      if (response.status !== 200) {
        throw new Error(`Backend returned code ${response.status}, body: ${response.statusText}`);
      }

      const responseData = response.data;
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
      {
      errors.length > 0 && (
        <div className="mb-4">
          <Alert variant="danger">
            {errors.map((error, index) => (
              <div key={index}>{t(error)}</div>
            ))}
          </Alert>
        </div>
      )
    }
      <Form className="container mt-5">
        <Row>
          <FormInput
            label="editOffer.title"
            value={title}
            onChange={(e: any) => setTitle(e.target.value)}
            errors={errors}
            formError="error.title.required" controlId={""} />
        </Row>
        <Row>
          <FormInput
            label="editOffer.description"
            value={description}
            onChange={(e: any) => setDescription(e.target.value)}
            errors={errors}
            formError="error.description.required" controlId={""} />
        </Row>
        <Row>
          <Col>
            <Form.Group controlId="formDepartment">
              <Form.Label>{t("addOffer.departmentCode")}</Form.Label>
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
                onChange={(e) => handleDateChange(setInternshipStartDate, e.target.value)}
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
                onChange={(e) => handleDateChange(setInternshipEndDate, e.target.value)}
              />
              {renderDateInputError("addOffer.errors.endDateRequired")}
              {renderDateInputError("addOffer.errors.endDateBeforeStartDate")}
            </Form.Group>
          </Col>

          <Col md="4">
            <Form.Group controlId="formOfferOfferDate">
              <Form.Label>{t("addOffer.offerEndDate")}</Form.Label>
              <Form.Control
                type="date"
                value={formatDateForInput(offerEndDate)}
                onChange={(e) => handleDateChange(setOfferEndDate, e.target.value)}
              />
              {renderDateInputError("addOffer.errors.offerEndDateRequired")}
            </Form.Group>
          </Col>
        </Row>
        <Row>
          <Col>
            <Form.Group controlId="formAvailablePlaces">
              <Form.Label>{t("addOffer.availablePlaces")}</Form.Label>
              <Form.Control type="number" value={availablePlaces} onChange={(e: any) => setAvailablePlaces(e.target.value)} />
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
