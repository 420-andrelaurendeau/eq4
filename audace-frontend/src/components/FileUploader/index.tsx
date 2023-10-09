import React, { useState } from "react";
import { Button, Form, FormControl } from "react-bootstrap";
import { useTranslation } from "react-i18next";
import { uploadFile } from "../../services/fileService";
import "./styles.css";
import { Student } from "../../model/user";

interface Props {
  student: Student;
}

const FileUploader = ({ student }: Props) => {
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
        setSuccessMessage(t("upload.success"));
      })
      .catch((err) => {
        console.log(err);
      });
  };

  return (
    <>
      <h3>{t("upload.CvFormTitle")}</h3>
      <Form>
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
        {successMessage !== "" && (
          <p className="successMessage">{successMessage}</p>
        )}
      </Form>
    </>
  );
};

export default FileUploader;
