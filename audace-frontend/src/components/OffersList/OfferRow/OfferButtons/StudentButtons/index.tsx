import { Button } from "react-bootstrap";
import { useTranslation } from "react-i18next";
import {apply, getCvsByStudentId} from "../../../../../services/studentApplicationService";
import React, {useEffect, useState} from "react";
import {getUserId} from "../../../../../services/authService";
import {CV} from "../../../../../model/cv";
import {Offer} from "../../../../../model/offer";
import {Student} from "../../../../../model/user";
import Application from "../../../../../model/application";

interface Props {
    disabled?: boolean;
    offer: Offer
}

const StudentButtons = ({disabled, offer}: Props) => {
    const {t} = useTranslation();
    const [applicationMessage, setApplicationMessage] = useState("");
    const [applicationMessageColor, setApplicationMessageColor] = useState("");
    const studentId = getUserId();
    const [cv, setCv] = useState<CV>();

    useEffect(() => {
        if (studentId === undefined) return;

        getCvsByStudentId(parseInt(studentId!))
            .then((res) => {
                setCv(res.data[0]);
            })
            .catch((err) => {
                console.log("getCvsByStudentId error", err);
            });
    }, [studentId]);

    const handleApply = async (event: { stopPropagation: () => void; }) => {
        event.stopPropagation();
        if (!studentId || !cv) {
            throw new Error("Student/CV null");
        }

        try {
            const tempStudent: Student = {
                id: parseInt(studentId!),
                firstName: "",
                lastName: "",
                email: "",
                phone: "",
                address: "",
                type: "student",
                studentNumber: "",
                password: "string"
            };

            const applicationData: Application = {
                id: 1000,
                offer: offer,
                cv: cv,
                student: tempStudent,
            }

            const response = await apply(applicationData);
            console.log(response);

            setApplicationMessage(t("offersList.applicationMessageSuccess"));
            setApplicationMessageColor("green");
        } catch (error) {
            setApplicationMessage(t("offersList.applicationMessageFailure") + error);
            setApplicationMessageColor("red");
        }
    };

    return (
        <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center', justifyContent: 'center' }}>
            <Button disabled={disabled} onClick={handleApply}>
                {t("offersList.applyButton")}
            </Button>
            <p style={{ color: applicationMessageColor }}>{applicationMessage}</p>
        </div>
    );
};

export default StudentButtons;