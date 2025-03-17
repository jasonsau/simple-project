export interface AuthUserRequest {
    username: string;
    password: string;
}

export interface AuthUserResponse {
    status: string;
    body : {
        token: string;
        refreshToken: string;
        duration: number;
        username: string
    }
}