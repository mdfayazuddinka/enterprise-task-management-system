export interface SignupRequestDto {
  userName: string;
  email: string;
  password: string;
  role?: string[];
}