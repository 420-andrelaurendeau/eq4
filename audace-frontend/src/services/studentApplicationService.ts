import { AxiosResponse } from "axios";
import Application from "../model/application";
import http from "../constants/http";
import { CV } from "../model/cv";

export const apply = async (application: Application): Promise<AxiosResponse> => {
    return http.post(`/students/${application.student!.id}/applications`, application);
}

export const getCvsByStudentId = async (studentId: number): Promise<AxiosResponse<CV[]>> => {
    return http.get<CV[]>(`/students/cvs/${studentId}`);
}