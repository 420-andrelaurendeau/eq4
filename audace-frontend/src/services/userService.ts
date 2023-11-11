import { AxiosResponse } from "axios";
import { Employer, Manager, Student, User } from "../model/user";
import http from "../constants/http";
import { EMPLOYER_PREFIX, MANAGER_PREFIX, STUDENT_PREFIX, USER_PREFIX } from "../constants/apiPrefixes";
import Notification from "../model/notification";

export const getEmployerById = async (id: number): Promise<AxiosResponse<Employer>> => {
    return http.get<Employer>(`${EMPLOYER_PREFIX}/${id}`);
};

export const getManagerById = async (id: number): Promise<AxiosResponse<Manager>> => {
    return http.get<Manager>(`${MANAGER_PREFIX}/${id}`);
}

export const getStudentById = async (id: number): Promise<AxiosResponse<Student>> => {
    return http.get<Student>(`${STUDENT_PREFIX}/${id}`);
}

export const getUserById = async (id: number): Promise<AxiosResponse<User>> => {
    return http.get<User>(`${USER_PREFIX}/${id}`);
}
export const getNotificationsByUserId = async (id: number): Promise<AxiosResponse<Notification[]>> => { //TODO : I'm a disgrace... why am I tho??? I forgot why I left that here
    return http.get<Notification[]>(`${USER_PREFIX}/notifications/${id}`);
}