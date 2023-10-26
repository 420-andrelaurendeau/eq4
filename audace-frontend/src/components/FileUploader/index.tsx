import React, { useState } from "react";
import { Alert, Button, Form, FormControl } from "react-bootstrap";
import { useTranslation } from "react-i18next";
import { uploadFile } from "../../services/fileService";
import { Student } from "../../model/user";

interface Props {
  student: Student;
  onUploadSuccess: () => void;
}

const FileUploader = ({ student, onUploadSuccess }: Props) => {
  const { t } = useTranslation();
  const [file, setFile] = useState<File>();
  const [successMessage, setSuccessMessage] = useState<string>("");

  const validateForm = () => {
    return file !== undefined;
  };

  const submitForm = () => {
    if (!validateForm()) return;

    uploadFile(student.id!, file!)
      .then((_) => {
        setSuccessMessage("upload.success");
        onUploadSuccess();
      })
      .catch((err) => {
        console.log(err);
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
              if (e.target.files) {
                setFile(e.target.files[0]);
              }
            }}
          ></FormControl>
        </Form.Group>

        <Button variant="primary" className="mt-3" onClick={submitForm}>
          {t("upload.submit")}
        </Button>
      </Form>
      {successMessage !== "" && (
        <Alert variant="success">{t(successMessage)}</Alert>
      )}
    </>
  );
};

export default FileUploader;
