import { Role } from "../roleEnum";

export interface SignUpResponseDto {
    userName: string;
    email: string;
    role: Role;
    userId: string;
}
