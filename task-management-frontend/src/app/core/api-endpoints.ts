import { environment } from '../environments/environment';

const BASE = environment.api.baseUrl;

export const API_ENDPOINTS = {
  AUTH: {
    LOGIN: `${BASE}${environment.api.auth}/login`,
    SIGNUP: `${BASE}${environment.api.auth}/signup`
  },
  TASKS: {
    ROOT: `${BASE}${environment.api.tasks}`,
    BY_ID: (id: string) => `${BASE}${environment.api.tasks}/${id}`
  }
};
