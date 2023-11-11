import { AxiosResponse } from "axios";
import http from "../constants/http";
import { USER_PREFIX } from "../constants/apiPrefixes";
import Notification from "../model/notification";

export const getNotificationsByUserId = async (id: number): Promise<AxiosResponse<Notification[]>> => {
    return http.get<Notification[]>(`${USER_PREFIX}/notifications/${id}`);
}

export const deleteNotificationById = async (id: number): Promise<AxiosResponse<void>> => {
    return http.delete<void>(`${USER_PREFIX}/deleteNotificationById/${id}`);
}

export const deleteAllNotificationsByUserId = async (id: number): Promise<AxiosResponse<void>> => {
    return http.delete<void>(`${USER_PREFIX}/deleteAllNotificationsByUserId/${id}`);
}