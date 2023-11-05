import {AxiosResponse} from "axios";
import Application from "../model/application";
import http from "../constants/http";
import {MANAGER_PREFIX, STUDENT_PREFIX} from "../constants/apiPrefixes";
import {CV} from "../model/cv";
import {Student, StudentsByInternshipFoundStatus} from "../model/user";
import {Department} from "../model/department";
import {Contract} from "../model/contract";

export const getAcceptedApplicationsByDepartment = async (managerId: number, departmentId: number): Promise<AxiosResponse<Application[]>> => {
    return http.get<Application[]>(`${MANAGER_PREFIX}/${managerId}/acceptedApplications/${departmentId}`);
}

export const getStudentByCv = async (cv: CV): Promise<AxiosResponse<Student>> => {
    return http.get<Student>(`${STUDENT_PREFIX}/${cv.student.id}`);
}

export const getDepartmentByManager = async (managerId: number): Promise<AxiosResponse<Department>> => {
    return http.get<Department>(`${MANAGER_PREFIX}/${managerId}/department`);
}

export const getApplicationById = async (id: number): Promise<AxiosResponse<Application>> => {
    return http.get<Application>(`${MANAGER_PREFIX}/applications/${id}`);
}

export const createContract = async (contract: Contract): Promise<AxiosResponse> => {
    return http.post(`${MANAGER_PREFIX}/contracts`, contract);
}

export const getContractById = async (id: number): Promise<AxiosResponse> => {
    return http.get(`${MANAGER_PREFIX}/contracts/${id}`);
}

export async function getStudentsByInternshipStatusTest (departmentId: number): Promise<any> {
    try {
        const response: AxiosResponse<any> = await http.get(`${MANAGER_PREFIX}/studentsWithInternshipFoundStatus/${departmentId}`);

        return {
            studentsWithInternship: {
                students: response.data.studentsWithInternship,
                status: 'INTERN',
            },
            studentsWithAcceptedResponse: {
                students: response.data.studentsWithAcceptedResponse,
                status: 'ACCEPTED',
            },
            studentsWithPendingResponse: {
                students: response.data.studentsWithPendingResponse,
                status: 'PENDING',
            },
            studentsWithRefusedResponse: {
                students: response.data.studentsWithRefusedResponse,
                status: 'REFUSED',
            },
            studentsWithoutApplications: {
                students: response.data.studentsWithoutApplications,
                status: 'NO_APPLICATIONS',
            },
        };
    } catch (error) {
        throw error;
    }
}

export const getStudentsByInternshipStatus = async (departmentId: number): Promise<AxiosResponse<any>> => {
    return http.get<any>(`${MANAGER_PREFIX}/studentsWithInternshipFoundStatus/${departmentId}`);
}
