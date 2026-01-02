import { Injectable } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class ThemeService {

  private readonly THEME_KEY = 'theme';

  initTheme() {
    const saved = localStorage.getItem(this.THEME_KEY) || 'light';
    this.setTheme(saved);
  }

  toggleTheme() {
    const current = document.body.getAttribute('data-theme');
    this.setTheme(current === 'dark' ? 'light' : 'dark');
  }

  setTheme(theme: string) {
    document.body.setAttribute('data-theme', theme);
    localStorage.setItem(this.THEME_KEY, theme);
  }

  isDark(): boolean {
    return document.body.getAttribute('data-theme') === 'dark';
  }
}
