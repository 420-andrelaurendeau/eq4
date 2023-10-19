import { AxiosResponse } from "axios";
import { Employer, Manager, Student, User } from "../model/user";
import http from "../constants/http";

export const getEmployerById = async (id: number): Promise<AxiosResponse<Employer>> => {
    return http.get<Employer>(`/employers/${id}`);
};

export const getManagerById = async (id: number): Promise<AxiosResponse<Manager>> => {
    return http.get<Manager>(`/managers/${id}`);
}

export const getStudentById = async (id: number): Promise<AxiosResponse<Student>> => {
    return http.get<Student>(`/students/${id}`);
}

export const getUserById = async (id: number): Promise<AxiosResponse<User>> => {
    return http.get<User>(`/users/${id}`);
}