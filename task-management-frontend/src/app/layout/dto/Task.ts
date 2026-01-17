export enum TaskStatus {
  TO_DO = 'TO_DO',
  IN_PROGRESS = 'IN_PROGRESS',
  COMPLETED = 'COMPLETED',
  BLOCKED = 'BLOCKED',
  QA_TESTING = 'QA_TESTING',
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
