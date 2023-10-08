export interface LoginRequest {
    identification: string;
    password: string;
}

export interface TimedJwt {
    jwt: string;
    expiration: number;
}