import { AxiosResponse } from "axios";
import http from "../constants/http";
import { CV } from "../model/cv";
import {MANAGER_PREFIX, STUDENT_PREFIX} from "../constants/apiPrefixes";

export const getManagerCvsByDepartment = async (departmentId: number): Promise<AxiosResponse<CV[]>> => {
    return http.get<CV[]>(`${MANAGER_PREFIX}/cvs/${departmentId}`);
}

export const acceptCv = async (cvId: number): Promise<AxiosResponse<CV>> => {
    return http.post<CV>(`${MANAGER_PREFIX}/accept_cv/${cvId}`);
}

export const refuseCv = async (cvId: number): Promise<AxiosResponse<CV>> => {
    return http.post<CV>(`${MANAGER_PREFIX}/refuse_cv/${cvId}`);
}

export const getCvsByStudentId = async (studentId: number): Promise<AxiosResponse<CV[]>> => {
    return http.get<CV[]>(`${STUDENT_PREFIX}/cvs/${studentId}`);
}