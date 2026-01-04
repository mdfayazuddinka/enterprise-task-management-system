import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { ThemeService } from './core/theme.service';
import { ToasterComponent } from './core/toaster/toaster.component';
import { CommonModule } from '@angular/common';


@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, ToasterComponent, CommonModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'task-management-frontend';

  constructor(private theme: ThemeService) {}
  

  // get isDark() {
  //   return this.theme.isDark();
  // }

  // toggleTheme() {
  //   this.theme.toggleTheme();
  // }
}
