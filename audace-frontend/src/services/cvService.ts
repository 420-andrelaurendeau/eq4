import { AxiosResponse } from "axios";
import http from "../constants/http";
import { CV } from "../model/cv";
import { MANAGER_PREFIX, STUDENT_PREFIX } from "../constants/apiPrefixes";

export const getManagerCvsByDepartment = async (
  departmentId: number,
  sessionId: number
): Promise<AxiosResponse<CV[]>> => {
  return http.get<CV[]>(`${MANAGER_PREFIX}/cvs/${departmentId}/${sessionId}`);
};

export const acceptCv = async (
  managerId: number,
  cvId: number
): Promise<AxiosResponse<CV>> => {
  return http.post<CV>(`${MANAGER_PREFIX}/${managerId}/accept_cv/${cvId}`);
};

export const refuseCv = async (
  managerId: number,
  cvId: number
): Promise<AxiosResponse<CV>> => {
  return http.post<CV>(`${MANAGER_PREFIX}/${managerId}/refuse_cv/${cvId}`);
};

export const getCvsByStudentId = async (
  studentId: number
): Promise<AxiosResponse<CV[]>> => {
  return http.get<CV[]>(`${STUDENT_PREFIX}/cvs/${studentId}`);
};
