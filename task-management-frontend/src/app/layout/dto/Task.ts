import { ProjectPriority } from "../../features/enums/project";

export type TaskStatus = 'PENDING' | 'IN_PROGRESS' | 'COMPLETED';
export type TaskPriority = 'LOW' | 'NORMAL' | 'HIGH';

export interface TaskDto {
  id: string;
  title: string;
  description: string;
  priority: TaskPriority;
  status: TaskStatus;
  createdBy: string;
  createdDate: string;
  dueDate: string;
  assignedTo: string;
  comments: string;
}

export interface TaskCreationDto {
  title: string;
  projectId: string;
  priority: TaskPriority;
  status: TaskStatus;
  assignedTo: string;
  createdBy: string;
  createdDate: string;
  dueDate: string;
  description?: string;
  comment?: string;
}
