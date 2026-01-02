import { bootstrapApplication } from '@angular/platform-browser';
import { AppComponent } from './app/app.component';
import { provideRouter } from '@angular/router';
import { routes } from './app/app.routes';
import { ThemeService } from './app/core/theme.service';
import { provideHttpClient } from '@angular/common/http';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';


bootstrapApplication(AppComponent, {
  providers: [
    provideRouter(routes),
    provideHttpClient(), provideAnimationsAsync()
  ]
})
.then(appRef => {
  const themeService = appRef.injector.get(ThemeService);
  themeService.initTheme();
})
.catch((err) => console.error(err));
