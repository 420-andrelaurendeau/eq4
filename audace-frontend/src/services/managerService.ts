import {AxiosResponse} from "axios";
import Application from "../model/application";
import http from "../constants/http";
import {MANAGER_PREFIX, STUDENT_PREFIX} from "../constants/apiPrefixes";
import {getUserId} from "./authService";
import {CV} from "../model/cv";
import {Student} from "../model/user";
import {Department} from "../model/department";

export const getAcceptedApplicationsByDepartment = async (departmentId: number): Promise<AxiosResponse<Application[]>> => {
    return http.get<Application[]>(`${MANAGER_PREFIX}/${getUserId()}/acceptedApplications/${departmentId}`);
}

export const getStudentByCv = async (cv: CV): Promise<AxiosResponse<Student>> => {
    return http.get<Student>(`${STUDENT_PREFIX}/${cv.student.id}`);
}

export const getStudentByApplication = async (application: Application): Promise<AxiosResponse<Student>> => {
    const studentCv: CV = application.cv!;

    const applicationStudent = await getStudentByCv(studentCv);

    return http.get<Student>(`${STUDENT_PREFIX}/${applicationStudent.data.id}`);
}

export const getDepartmentByManager = async (managerId: number): Promise<AxiosResponse<Department>> => {
    return http.get<Department>(`${MANAGER_PREFIX}/${managerId}/department`);
}
