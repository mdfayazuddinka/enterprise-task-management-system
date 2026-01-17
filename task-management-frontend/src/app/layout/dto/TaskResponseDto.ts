import { ProjectPriority } from "../../features/enums/project";
import { TaskStatus } from "./Task";

export interface TaskResponseDto {
  id: string;
  title: string;
  description: string;
  priority: ProjectPriority;   // enum defined separately
  status: TaskStatus;       // enum defined separately
  createdBy: string;
  createdDate: Date;        // can also be string if coming as ISO
  dueDate: Date;
  assignedTo: string;
  comments: string;
}
