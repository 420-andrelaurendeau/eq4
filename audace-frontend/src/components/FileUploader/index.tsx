import React, { useState } from "react";
import { Alert, Button, Form, FormControl } from "react-bootstrap";
import { useTranslation } from "react-i18next";
import { uploadFile } from "../../services/fileService";
import { Student } from "../../model/user";
import { getCvsByStudentId } from "../../services/cvService";
import { useCVContext } from "../../contextsholders/providers/CVContextHolder";

interface Props {
  student: Student;
}

const FileUploader = ({ student }: Props) => {
  const { t } = useTranslation();
  const [file, setFile] = useState<File>();
  const [successMessage, setSuccessMessage] = useState<string>("");
  const [hasErrorOccurred, setHasErrorOccurred] = useState<boolean>(false);
  const { setCvs } = useCVContext();

  const validateForm = () => {
    return file !== undefined;
  };

  const submitForm = () => {
    if (!validateForm()) return;

    uploadFile(student.id!, file!)
      .then((_) => {
        setSuccessMessage("upload.success");
        handleUploadSuccess();
        setHasErrorOccurred(false);
      })
      .catch((err) => {
        setHasErrorOccurred(true);
      });
  };

  const handleUploadSuccess = () => {
    getCvsByStudentId(student!.id!)
      .then((res) => {
        setCvs(res.data);
      })
      .catch((err) => {
        setHasErrorOccurred(true);
      });
  };

  return (
    <>
      <h3>{t("upload.CvFormTitle")}</h3>
      <Form className="my-3">
        <Form.Group controlId="formBasicCvFile">
          <Form.Label>{t("upload.file")}</Form.Label>
          <FormControl
              type="file"
              onChange={(e: React.ChangeEvent<HTMLInputElement>) => {
                if (e.target.files) setFile(e.target.files[0]);
              }}
          />
        </Form.Group>

        <Button
          variant="outline-primary"
          className="mt-3 text-dark"
          onClick={submitForm}
        >
          {t("upload.submit")}
        </Button>
      </Form>
      {successMessage !== "" && (
        <Alert variant="success">{t(successMessage)}</Alert>
      )}
      {hasErrorOccurred && <Alert variant="danger">{t("upload.error")}</Alert>}
    </>
  );
};

export default FileUploader;