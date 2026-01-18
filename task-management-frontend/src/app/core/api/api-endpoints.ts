import { environment } from '../../environments/environment';

const BASE = environment.api.baseUrl;

console.log(BASE);

export const API_ENDPOINTS = {
  AUTH: {
    LOGIN: `${BASE}${environment.api.auth}/login`,
    SIGNUP: `${BASE}${environment.api.auth}/signup`,
    GET_USER_NAMES: `${BASE}${environment.api.auth}/getAllUserNames`
  },
  TASKS: {
    ROOT: `${BASE}${environment.api.tasks}`,
    BY_ID: (id: string) => `${BASE}${environment.api.tasks}/${id}`
  },
  PROJECT: {
    ROOT: `${BASE}${environment.api.project}`,
  }
};
