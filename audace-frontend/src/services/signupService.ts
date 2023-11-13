import { AxiosResponse } from "axios";
import { Employer, Student } from "../model/user";
import http from "../constants/http";
import { AUTH_PREFIX } from "../constants/apiPrefixes";

export const employerSignup = async (
  employer: Employer
): Promise<AxiosResponse> => {
  return http.post(`${AUTH_PREFIX}/signup/employer`, employer);
};

export const studentSignup = async (
  student: Student,
  depCode: string
): Promise<AxiosResponse> => {
  return http.post(`${AUTH_PREFIX}/signup/student/${depCode}`, student);
};
