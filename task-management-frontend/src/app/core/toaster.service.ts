import { Injectable, signal } from '@angular/core';

export type ToastType = 'success' | 'error' | 'info';

export interface Toast {
  message: string;
  type: ToastType;
  id: number;
}

@Injectable({ providedIn: 'root' })
export class ToasterService {
  private _toasts = signal<Toast[]>([]);
  private counter = 0;

  toasts = this._toasts.asReadonly();

  show(message: string, type: ToastType = 'info') {
    const toast: Toast = { message, type, id: ++this.counter };
    this._toasts.update(list => [...list, toast]);

    // Auto-remove after 3 seconds
    setTimeout(() => this.remove(toast.id), 3000);
  }

  remove(id: number) {
    this._toasts.update(list => list.filter(t => t.id !== id));
  }
}

