export interface User {
    id?: number;
    email: string;
    password: string;
}

export interface Student extends User{
    studentId: string;
}