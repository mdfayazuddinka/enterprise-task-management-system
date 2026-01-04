import { Role } from "../../auth/roleEnum";

export interface UserDto {
  userName: string;
  userId: string;
  email: string;
  role: Role[];
}