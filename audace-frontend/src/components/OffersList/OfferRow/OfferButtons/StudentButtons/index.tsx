import { Button } from "react-bootstrap";
import { useTranslation } from "react-i18next";
import {applyStage, getCvsByStudentId} from "../../../../../services/studentApplicationService";
import React, {useEffect, useState} from "react";
import {getUserId} from "../../../../../services/authService";
import {CV} from "../../../../../model/cv";
import {Offer} from "../../../../../model/offer";

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

        getCvsByStudentId(parseInt(getUserId()!))
            .then((res) => {
                setCv(res.data[0]);
            })
            .catch((err) => {
                console.log("getCvsByStudentId error", err);
            });
    }, [studentId]);

    const handleApply = (event: { stopPropagation: () => void; }) => {
        try {
            const applicationMessage = applyStage(studentId!, cv!, offer);
            setApplicationMessage(t("offersList.applicationMessageSuccess"));
            setApplicationMessageColor("green");
        } catch (error) {
            setApplicationMessage(t("offersList.applicationMessageFailure") + error);
            setApplicationMessageColor("red");
        }
        event.stopPropagation();
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