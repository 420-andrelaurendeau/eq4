import { AxiosResponse } from "axios";
import Application from "../model/application";
import http from "../constants/http";
import { CV } from "../model/cv";
import {Student} from "../model/user";
import {Offer} from "../model/offer";

export const apply = async (application: Application): Promise<AxiosResponse> => {
    return http.post(`/students/applications/${application.student!.id}`, application);
}

export const getCvsByStudentId = async (studentId: number): Promise<AxiosResponse<CV[]>> => {
    return http.get<CV[]>(`/students/cvs/${studentId}`);
}