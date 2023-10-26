import { AxiosResponse } from "axios";
import http from "../constants/http";
import { CV } from "../model/cv";

export const getManagerCvsByDepartment = async (departmentId: number, sessionId: number): Promise<AxiosResponse<CV[]>> => {
    return http.get<CV[]>(`/managers/cvs/${departmentId}/${sessionId}`);
}
//CvButton
export const acceptCv = async (managerId : number, cvId: number): Promise<AxiosResponse<CV>> => {
    return http.post<CV>(`/managers/${managerId}/accept_cv/${cvId}`);
}

export const refuseCv = async (managerId : number, cvId: number): Promise<AxiosResponse<CV>> => {
    return http.post<CV>(`/managers/${managerId}/refuse_cv/${cvId}`);
}