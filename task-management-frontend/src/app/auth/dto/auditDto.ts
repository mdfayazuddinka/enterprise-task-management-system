import { UserInfoDto } from "./userInfoDto";

export interface AuditDto {
  owner: UserInfoDto;
  createdDate: string;
  lastModifiedBy: string;
  lastModifiedDate: string;
}
