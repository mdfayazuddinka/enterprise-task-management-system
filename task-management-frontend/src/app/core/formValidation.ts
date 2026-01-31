import { AbstractControl, ValidationErrors, ValidatorFn } from "@angular/forms";

export default class formValidation {
    static match(controlName: string, matchingControlName: string) {
        return (controls: AbstractControl) => {
            const control = controls.get(controlName);
            const matchingControl = controls.get(matchingControlName);

            if (matchingControl?.errors && !matchingControl.errors['matching']) {
                return null;
            }

            if (control?.value !== matchingControl?.value) {
                controls.get(matchingControlName)?.setErrors({ matching: true });
                return { matching: true };
            } else {
                return null;
            }
        };
    }
}

export const dueDateAfterCreatedValidator: ValidatorFn = (control: AbstractControl): ValidationErrors | null => {
    const createdDate = new Date(control.get('createdDate')?.value);
    const dueDate = new Date(control.get('dueDate')?.value);
    if (createdDate && dueDate && dueDate <= createdDate) {
        return { dueDateInvalid: true }; // error object
    }
    return null; // valid
};