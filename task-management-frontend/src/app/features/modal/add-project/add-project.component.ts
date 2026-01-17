import { Component, EventEmitter, Output } from '@angular/core';
import { ProjectCategory, ProjectType, ProjectPriority, ProjectStatus } from '../../enums/project';
import { PROJECT_SUBCATEGORY_MAP } from '../project-subcategory.map';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ProjectService } from '../../../core/project.service';
import { ToasterService } from '../../../core/toaster.service';

@Component({
  selector: 'app-add-project',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './add-project.component.html',
  styleUrl: './add-project.component.scss'
})
export class AddProjectComponent {
  @Output() close = new EventEmitter<void>();
  @Output() projectdata = new EventEmitter<any>();

  categories = Object.values(ProjectCategory);
  types = Object.values(ProjectType);
  priorities = Object.values(ProjectPriority);
  statuses = Object.values(ProjectStatus);

  subCategories: string[] = [];

  project = {
    category: '' as ProjectCategory | '',
    subCategory: '',
    name: '',
    type: '' as ProjectType | '',
    priority: ProjectPriority.LOW,
    status: ProjectStatus.TO_DO,
    startDate: '',
    dueDate: ''
  };

  constructor(
      private router: Router,
      private route: ActivatedRoute,
      private projectService: ProjectService,
      private toaster: ToasterService
    ) {}


  onCategoryChange(category: string) {
    const typedCategory = category as ProjectCategory;
    this.project.category = typedCategory;
    this.subCategories = PROJECT_SUBCATEGORY_MAP[typedCategory] || [];
    this.project.subCategory = '';
  }

  submit() {
    this.projectService.createProject(this.project).subscribe({
      next: (project) => {
        this.closeModal();
        this.projectService.setSelectedProject(project);
      },
      error: (err) => {
        console.log("error: ", err)
        this.toaster.show(err, 'error')
      }
    })
  }


  closeModal() {
    this.router.navigate(
      [{ outlets: { popup: null } }],
      { relativeTo: this.route.parent}
    );
  }
}
