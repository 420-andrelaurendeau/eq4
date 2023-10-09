import React, { useState, useEffect } from "react";
import {Button, Form, FormControl, Container } from "react-bootstrap";
import { useTranslation } from "react-i18next";
import { uploadFile } from "../../services/fileService";
import './styles.css'

const FileUploader = () => {
    const {t} = useTranslation();
    const [file, setFile] = useState<File>();
    const [studentId, setStudentId] = useState<number>();
    const [successMessage, setSuccessMessage] = useState<string>("");

    useEffect(() => {
        // setStudentId(JSON.parse(sessionStorage.getItem("user")!).id);
    }, []);

    const validateForm = () => {
        return file !== undefined;
    }

    const submitForm = () => {
        if (!validateForm()) return;

        setStudentId(JSON.parse(sessionStorage.getItem("user")!).id);

        uploadFile(studentId!, file!)
        .then((_) => {
            setSuccessMessage(t("upload.success"));
        })
        .catch((err) => {
            console.log(err)
        });
    };

    return (
        <Container>
            <h3>{t("upload.CvFormTitle")}</h3>
            <Form>
                <Form.Group controlId="formBasicCvFile">
                    <Form.Label>{t("upload.file")}</Form.Label>
                    <FormControl type="file"
                        onChange={(e: React.ChangeEvent<HTMLInputElement>) => {
                            if (e.target.files) {
                                setFile(e.target.files[0]);
                            }
                        }}>
                    </FormControl>
                </Form.Group>

                <Button variant="primary" className="mt-3" onClick={submitForm}>
                        {t("upload.submit")}
                </Button>
                {successMessage !== "" && <p className="successMessage">{successMessage}</p>}
            </Form>
        </Container>
    );

};

export default FileUploader;
