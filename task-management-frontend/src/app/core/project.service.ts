import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, Subject } from 'rxjs';
import { ProjectCreationDto, ProjectDto } from '../features/enums/project';
import { HttpClient } from '@angular/common/http';
import { API_ENDPOINTS } from './api-endpoints';


@Injectable({ providedIn: 'root' })
export class ProjectService {
  private projectSource = new BehaviorSubject<ProjectDto | null>(null);
  project$ = this.projectSource.asObservable();

  constructor(private http: HttpClient) {}

  setSelectedProject(project: ProjectDto) {
    this.projectSource.next(project);
  }

  createProject(projectCreationDto: ProjectCreationDto)  {
    return this.http.post<ProjectDto> (
          API_ENDPOINTS.PROJECT.ROOT + '/add-project',
          projectCreationDto
        );
  }

  getAllProjects() {
    return this.http.get<ProjectDto[]>(
      API_ENDPOINTS.PROJECT.ROOT+ '/getAllProjects'
    )
  }
}

