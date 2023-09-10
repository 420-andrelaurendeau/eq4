import { AxiosResponse } from "axios";
import { Student } from "../model/user";
import http from "../constants/http";

export const studentSignup = async (student: Student): Promise<AxiosResponse> => {
    return http.post('/students/signup', student);
}