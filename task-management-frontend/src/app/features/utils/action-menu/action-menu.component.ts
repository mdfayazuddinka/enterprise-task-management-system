import { Component, EventEmitter, HostListener, Input, Output } from '@angular/core';
import { ActionMenuItem } from '../action-menu-items';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-action-menu',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './action-menu.component.html',
  styleUrl: './action-menu.component.scss'
})
export class ActionMenuComponent {
  @Input() items: ActionMenuItem[] = [];

  @Output() actionSelected = new EventEmitter<string>();

  isOpen = false;

  toggle(event: MouseEvent) {
    event.stopPropagation();
    this.isOpen = !this.isOpen;
  }

  @HostListener('document:click')
  close() {
    this.isOpen = false;
  }

  onSelect(action: string) {
    this.actionSelected.emit(action);
    this.isOpen = false;
  }
}
