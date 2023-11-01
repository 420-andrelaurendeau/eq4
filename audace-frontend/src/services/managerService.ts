import {AxiosResponse} from "axios";
import Application from "../model/application";
import http from "../constants/http";
import {MANAGER_PREFIX, STUDENT_PREFIX} from "../constants/apiPrefixes";
import {CV} from "../model/cv";
import {Student} from "../model/user";
import {Department} from "../model/department";

export const getAcceptedApplicationsByDepartment = async (managerId: number, departmentId: number): Promise<AxiosResponse<Application[]>> => {
    return http.get<Application[]>(`${MANAGER_PREFIX}/${managerId}/acceptedApplications/${departmentId}`);
}

export const getStudentByCv = async (cv: CV): Promise<AxiosResponse<Student>> => {
    return http.get<Student>(`${STUDENT_PREFIX}/${cv.student.id}`);
}

export const getDepartmentByManager = async (managerId: number): Promise<AxiosResponse<Department>> => {
    return http.get<Department>(`${MANAGER_PREFIX}/${managerId}/department`);
}