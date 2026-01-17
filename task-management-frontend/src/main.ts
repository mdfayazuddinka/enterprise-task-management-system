import { bootstrapApplication } from '@angular/platform-browser';
import { AppComponent } from './app/app.component';
import { provideRouter } from '@angular/router';
import { routes } from './app/app.routes';
import { ThemeService } from './app/core/theme.service';
import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { AuthInterceptor } from './app/auth/auth.interceptor';


bootstrapApplication(AppComponent, {
  providers: [
    provideRouter(routes),
    provideHttpClient(withInterceptors([AuthInterceptor])), provideAnimationsAsync()
  ]
})
.then(appRef => {
  const themeService = appRef.injector.get(ThemeService);
  themeService.initTheme();
})
.catch((err) => console.error(err));
