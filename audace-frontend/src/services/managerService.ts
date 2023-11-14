import { AxiosResponse } from "axios";
import Application from "../model/application";
import http from "../constants/http";
import { MANAGER_PREFIX, STUDENT_PREFIX } from "../constants/apiPrefixes";
import { CV } from "../model/cv";
import {
  Student,
  StudentsByInternshipFoundStatus,
  mapStudentsWithStatus,
} from "../model/user";
import { Department } from "../model/department";
import { Contract } from "../model/contract";

export const getAcceptedApplicationsByDepartment = async (
  managerId: number,
  departmentId: number
): Promise<AxiosResponse<Application[]>> => {
  return http.get<Application[]>(
    `${MANAGER_PREFIX}/${managerId}/acceptedApplications/${departmentId}`
  );
};

export const getStudentByCv = async (
  cv: CV
): Promise<AxiosResponse<Student>> => {
  return http.get<Student>(`${STUDENT_PREFIX}/${cv.student.id}`);
};

export const getDepartmentByManager = async (
  managerId: number
): Promise<AxiosResponse<Department>> => {
  return http.get<Department>(`${MANAGER_PREFIX}/${managerId}/department`);
};

export const getApplicationById = async (
  id: number
): Promise<AxiosResponse<Application>> => {
  return http.get<Application>(`${MANAGER_PREFIX}/applications/${id}`);
};

export const createContract = async (
  contract: Contract
): Promise<AxiosResponse> => {
  return http.post(`${MANAGER_PREFIX}/contracts`, contract);
};

export const getContractById = async (id: number): Promise<AxiosResponse> => {
  return http.get(`${MANAGER_PREFIX}/contracts/${id}`);
};

export const getStudentsByInternshipStatus = async (
  departmentId: number
): Promise<StudentsByInternshipFoundStatus> => {
  try {
    const response: AxiosResponse<StudentsByInternshipFoundStatus> =
      await http.get(
        `${MANAGER_PREFIX}/studentsWithInternshipFoundStatus/${departmentId}`
      );

    const statusKeys = Object.keys(response.data);

    return statusKeys.reduce((result, statusKey) => {
      result[statusKey] = mapStudentsWithStatus(statusKey, response.data);
      return result;
    }, {} as StudentsByInternshipFoundStatus);
  } catch (error) {
    throw error;
  }
};
