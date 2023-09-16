import { AxiosResponse } from "axios";
import { Employer, Student } from "../model/user";
import http from "../constants/http";

export const employerSignup = async (employer: Employer): Promise<AxiosResponse> => {
    return http.post('/employer/signup', employer);
}

export const studentSignup = async (student: Student, depCode: string): Promise<AxiosResponse> => {
    return http.post(`/students/signup/${depCode}`, student);
}