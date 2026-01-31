import { Injectable } from '@angular/core';
import { TaskCreationDto, TaskDto, TaskEditDto } from '../layout/dto/Task';
import { API_ENDPOINTS } from '../core/api/api-endpoints';
import { TaskResponseDto } from '../layout/dto/TaskResponseDto';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TaskService {

  constructor(private http: HttpClient) { }

  createTask(taskCreationDto: TaskCreationDto )  {
      return this.http.post<TaskResponseDto> (
            API_ENDPOINTS.TASKS.ROOT + '/add-task',
            taskCreationDto
          );
  }

  getTasksByProjectId(projectId: string): Observable<TaskDto[]> {
    return this.http.get<TaskDto[]>(
      API_ENDPOINTS.TASKS.ROOT + `/project/${projectId}`
    );
  }

  deleteTask(taskId: string): Observable<void> {
    return this.http.delete<void>(
      API_ENDPOINTS.TASKS.ROOT + `/delete-task/${taskId}`
    );
  }

  updateTask(task: TaskEditDto): Observable<TaskDto> {
    return this.http.put<TaskDto>(
      API_ENDPOINTS.TASKS.ROOT + `/update/${task.taskId}`,
      task
    );
  }
}
