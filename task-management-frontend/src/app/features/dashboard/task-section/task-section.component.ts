import { CommonModule } from '@angular/common';
import { Component, ElementRef, EventEmitter, Input, Output, ViewChild } from '@angular/core';
import { TaskDto, TaskStatus } from '../../../layout/dto/Task';
import {RouterLink} from '@angular/router';
import { ActionMenuComponent } from '../../utils/action-menu/action-menu.component';
import { AddTaskComponent } from '../../modal/add-task/add-task.component';

@Component({
  selector: 'app-task-section',
  standalone: true,
  imports: [CommonModule, RouterLink, ActionMenuComponent, AddTaskComponent],
  templateUrl: './task-section.component.html',
  styleUrl: './task-section.component.scss'
})
export class TaskSectionComponent {
  @Input() title!: string;
  @Input() color!: 'gray' | 'yellow' | 'green';
  @Input() tasks: TaskDto[] = [];
  @Output() taskAction = new EventEmitter<{ action: string; task: TaskDto }>();

  @ViewChild('taskContainer') taskContainer!: ElementRef;
  hasScroll = false;

  taskMenuItems = [
  { label: 'Move to next stage', action: 'move' },
  { label: 'Edit', action: 'edit' },
  { label: 'Delete', action: 'delete', danger: true }
];

  handleTaskAction(action: string, task: any) {
    this.taskAction.emit({ action, task });
  }

  ngAfterViewInit() {
    this.checkScroll();
    window.addEventListener('resize', () => this.checkScroll());
  }

  private checkScroll() {
    const el = this.taskContainer.nativeElement;
    this.hasScroll = el.scrollHeight > el.clientHeight;
  }
}
