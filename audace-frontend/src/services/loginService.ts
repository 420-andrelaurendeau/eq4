import {AxiosResponse} from "axios";
import http from "../constants/http";
import {User} from "../model/user";

export const getAllUsers = async (): Promise<AxiosResponse<User[]>> =>  {
    return http.get<User[]>('/users')
}

export const getUser = async (id : number): Promise<AxiosResponse<User>> => {
    return http.get<User>(`users/${id}`);
}
