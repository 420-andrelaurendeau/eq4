import React, { useState, useEffect } from "react";
import { Alert, Button, Form, Row, Col } from "react-bootstrap";
import { useTranslation } from "react-i18next";
import FormInput from "../Signup/FormInput";
import { Offer } from "../../model/offer";
import { Department } from "../../model/department";
import { OfferStatus } from "../../model/offer";
import { useNavigate } from 'react-router-dom';
import { Employer } from "../../model/user";
import { getUserId } from "../../services/authService";
import { getEmployerById } from "../../services/userService";
import { employerCreateOffer } from "../../services/offerService";
import { getAllDepartments } from "../../services/departmentService";

const AddOffer: React.FC = () => {
  const { t } = useTranslation();
  const [title, setTitle] = useState<string>("");
  const [description, setDescription] = useState<string>("");
  const [department, setDepartment] = useState<Department>({ id: 1, name: "Génie Logiciel", code: "GLO" } as Department);
  const [internshipStartDate, setInternshipStartDate] = useState<Date>({} as Date);
  const [internshipEndDate, setInternshipEndDate] = useState<Date>({} as Date);
  const [offerEndDate, setOfferEndDate] = useState<Date>({} as Date);
  const [availablePlaces, setAvailablePlaces] = useState<number>(3);
  const [status, setStatus] = useState<OfferStatus>(OfferStatus.PENDING);
  const [employer, setEmployer] = useState<Employer>({} as Employer);
  const [errors, setErrors] = useState<string[]>([]);
  const [departments, setDepartments] = useState<Department[]>([]);
  const [showAlert, setShowAlert] = useState<boolean>(false);

  const resetAlert = () => {
    setShowAlert(false);
    setErrors([]);
  };

  const navigate = useNavigate();

  useEffect(() => {
    getEmployerById(parseInt(getUserId()!))
      .then((employerResponse) => {
        setEmployer(employerResponse.data);
      })
      .catch((error) => {
        console.error("There was an error fetching the employer:", error);
      });

    getAllDepartments()
      .then((res) => {
        const departmentData: Department[] = res.data;
        setDepartments(departmentData);
        setDepartment(departmentData[0]);
      })
      .catch((error) => {
        console.error("There was an error fetching the departments:", error);
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
    if (!department) errorsToDisplay.push("addOffer.errors.departmentCodeRequired");
    if (!internshipStartDate) errorsToDisplay.push("addOffer.errors.startDateRequired");
    if (!internshipEndDate) errorsToDisplay.push("addOffer.errors.endDateRequired");
    if (!offerEndDate) errorsToDisplay.push("addOffer.errors.offerEndDateRequired");
    if (!isValidDate(internshipStartDate)) errorsToDisplay.push("addOffer.errors.invalidStartDate");
    if (!isValidDate(internshipEndDate)) errorsToDisplay.push("addOffer.errors.invalidEndDate");
    if (!isValidDate(offerEndDate)) errorsToDisplay.push("addOffer.errors.invalidOfferEndDate");

    setErrors(errorsToDisplay);
    return errorsToDisplay.length === 0;
  };

  const handleSubmit = () => {
    if (validateForm()) {
      const formData: Offer = {
        title,
        description,
        department,
        internshipStartDate,
        internshipEndDate,
        offerEndDate,
        availablePlaces,
        offerStatus: OfferStatus.PENDING,
        employer
      };
      addOffer(formData);
      navigate(`/`);
    } else {
      setShowAlert(true);
    }
  };

  const addOffer = async (offerData: Offer) => {
    try {
      employerCreateOffer(offerData);

    } catch (error) {
      console.error("There was an error sending the data:", error);
    }
  };

  return (
    <>
    <h3 className="text-center">{t("addOffer.pageTitle")}</h3>
    <Form className="container mt-5">
    {showAlert && (
        <Alert variant="danger" onClose={resetAlert} dismissible>
          {errors.map((error, index) => (
            <p key={index}>{t(error)}</p>
          ))}
        </Alert>
      )}
      <Row>
        <FormInput
            label="addOffer.title"
            value={title}
            onChange={(e: any) => setTitle(e.target.value)}
            errors={errors}
            formError="addOffer.errors.titleRequired"
            controlId="formOfferTitle"
          />

          <Col md="4">
            <Form.Group controlId="formOfferDepartment">
              <Form.Label>{t("addOffer.departmentCode")}</Form.Label>
              <Form.Control
                as="select"
                value={department.toString()}
                isInvalid={errors.includes("addOffer.errors.departmentRequired")}
                onChange={(e: any) => setDepartment(e.target.value)}
              >
                <option value="" disabled>Select department...</option>
                {departments.map(dept => (
                  <option key={dept.code} value={dept.code}>
                    {dept.name}
                  </option>
                ))}
              </Form.Control>
              {errors.includes("addOffer.errors.departmentRequired") && (
                <Form.Control.Feedback type="invalid">
                  {t("addOffer.errors.departmentRequired")}
                </Form.Control.Feedback>
              )}
            </Form.Group>
          </Col>
        </Row>

        <Form.Group controlId="formOfferDescription">
          <Form.Label>{t("addOffer.description")}</Form.Label>
          <Form.Control
            as="textarea"
            value={description}
            isInvalid={errors.includes("addOffer.errors.descriptionRequired")}
            onChange={(e) => setDescription(e.target.value)}
          />
          {errors.includes("addOffer.errors.descriptionRequired") && (
            <Form.Control.Feedback type="invalid">
              {t("addOffer.errors.descriptionRequired")}
            </Form.Control.Feedback>
          )}
        </Form.Group>


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
