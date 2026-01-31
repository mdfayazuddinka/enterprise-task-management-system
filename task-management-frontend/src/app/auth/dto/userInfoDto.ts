import { Role } from "../roleEnum";

export interface UserInfoDto {
  userId: string;
  email: string;
  userName: string;
  role: Role[];
  exp: number;
  iat: number;
}
