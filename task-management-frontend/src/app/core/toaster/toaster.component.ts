import { Component } from '@angular/core';
import { ToasterService } from '../toaster.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-toaster',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './toaster.component.html',
  styleUrl: './toaster.component.scss'
})
export class ToasterComponent {
    constructor(public toastService: ToasterService) {}
}
