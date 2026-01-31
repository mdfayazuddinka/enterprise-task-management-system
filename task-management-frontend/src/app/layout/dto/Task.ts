import { AuditDto } from "../../auth/dto/auditDto";

export enum TaskStatus {
  READY = 'Ready For Development',
  DEVELOPING = 'Developing',
  BLOCKED = 'Blocked',
  QA_TESTING = 'QA Testing',
  DONE = 'Done',
}

export enum TaskPriority {
  LOW = 'LOW',
  MEDIUM = 'MEDIUM',
  HIGH = 'HIGH',
}

export enum TaskType {
  FEATURE = 'FEATURE',
  BUG = 'BUG',
  DEFECT = 'DEFECT',
  SUBTASK = 'SUBTASK',
}

export enum TaskLabel {
  UI = 'UI',
  Backend = 'Backend',
  Database = 'Database',
  API = 'API',
  Frontend = 'Frontend',
  Technical = 'Technical',
}


export interface TaskDto {
  id: string;
  title: string;
  taskId: string;
  description: string;
  priority: TaskPriority;
  status: TaskStatus;
  type: TaskType;
  dueDate: string;
  assignedTo: string;
  comments: string;
  projectId: string;
  audit: AuditDto;
  subTasks: any;
}

export interface TaskCreationDto {
  title: string;
  projectId: string;
  priority: TaskPriority;
  status: TaskStatus;
  assignedTo: string;
  createdDate: string;
  dueDate: string;
  description?: string;
  comments?: string;
}

export interface SubTaskCreationDto {
  title: string;
  description: string;
  assignedTo: string;
  status: TaskStatus;
  dueDate: string;
  priority: TaskPriority;
  parentTaskId: string;
  type: TaskType;
}

export interface TaskEditDto {
  id: string;
  title: string;
  taskId: string;
  description: string;
  priority: TaskPriority;
  status: TaskStatus;
  assignedTo: string;
  dueDate: string;
}
