import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { TaskDto } from '../../../layout/dto/Task';
import {RouterLink} from '@angular/router';
import { ActionMenuComponent } from '../../utils/action-menu/action-menu.component';

@Component({
  selector: 'app-task-section',
  standalone: true,
  imports: [CommonModule, RouterLink, ActionMenuComponent],
  templateUrl: './task-section.component.html',
  styleUrl: './task-section.component.scss'
})
export class TaskSectionComponent {
  @Input() title!: string;
  @Input() color!: 'gray' | 'yellow' | 'green';
  @Input() tasks: TaskDto[] = [];
  @Output() taskAction = new EventEmitter<{ action: string; task: TaskDto }>();

  taskMenuItems = [
  { label: 'Move to next stage', action: 'move' },
  { label: 'Edit', action: 'edit' },
  { label: 'Delete', action: 'delete', danger: true }
];

  handleTaskAction(action: string, task: any) {
    this.taskAction.emit({ action, task });
  }
}
