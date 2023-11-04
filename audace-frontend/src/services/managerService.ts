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

async function getStudentsByInternshipStatus(departmentId: number): Promise<StudentsByInternshipFoundStatus> {
    try {
        const response: AxiosResponse<StudentsByInternshipFoundStatus> = await http.get(`${MANAGER_PREFIX}/studentsByInternshipStatus/${departmentId}`);

        const {
            studentsWithInternship,
            studentsWithAcceptedResponse,
            studentsWithPendingResponse,
            studentsWithRefusedResponse,
            studentsWithoutApplications,
        } = response.data;

        return {
            studentsWithInternship: {
                students: studentsWithInternship.students,
                status: 'INTERN',
            },
            studentsWithAcceptedResponse: {
                students: studentsWithAcceptedResponse.students,
                status: 'ACCEPTED',
            },
            studentsWithPendingResponse: {
                students: studentsWithPendingResponse.students,
                status: 'PENDING',
            },
            studentsWithRefusedResponse: {
                students: studentsWithRefusedResponse.students,
                status: 'REFUSED',
            },
            studentsWithoutApplications: {
                students: studentsWithoutApplications.students,
                status: 'NO_APPLICATIONS',
            },
        };
    } catch (error) {
        throw error;
    }
}
