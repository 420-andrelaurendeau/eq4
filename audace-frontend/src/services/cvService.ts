import { AxiosResponse } from "axios";
import http from "../constants/http";
import { CV } from "../model/cv";

export const getManagerCvsByDepartment = async (departmentId: number): Promise<AxiosResponse<CV[]>> => {
    return http.get<CV[]>(`/managers/cvs/${departmentId}`);
}
//CvButton
export const acceptCv = async (cvId: number): Promise<AxiosResponse<CV>> => {
    return http.post<CV>(`/managers/accept_cv/${cvId}`);
}

export const refuseCv = async (cvId: number): Promise<AxiosResponse<CV>> => {
    return http.post<CV>(`/managers/refuse_cv/${cvId}`);
}