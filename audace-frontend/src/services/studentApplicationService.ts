import { AxiosResponse } from "axios";
import Application from "../model/application";
import http from "../constants/http";
import { CV } from "../model/cv";
import {Student} from "../model/user";
import {Offer} from "../model/offer";

export const apply = async (application: Application): Promise<AxiosResponse> => {
    return http.post(`/students/${application.student!.id}/applications`, application);
}

export const getCvsByStudentId = async (studentId: number): Promise<AxiosResponse<CV[]>> => {
    return http.get<CV[]>(`/students/cvs/${studentId}`);
}

export async function applyStage(studentId: string, cv: CV, offer: Offer) {
    if (!studentId || !cv) {
        throw new Error("Student or CV is null"); // TODO: eng & fr
    }

    const tempStudent: Student = {
        id: parseInt(studentId),
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

    return apply(applicationData);
}