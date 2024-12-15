import {AbstractControl, FormGroup, ValidationErrors, ValidatorFn, Validators} from "@angular/forms";
import {ErrorCode} from "src/app/shared/utils/validation/error.types";
import {isNil} from "lodash";

export const identityValidator = (relevantFieldName: string): ValidatorFn => {
	return (control: AbstractControl): ValidationErrors | null => {
		const controlParent = <FormGroup>control.parent;
		if (isNil(controlParent)) {
			return null;
		}
		const relevantControl = controlParent.get(relevantFieldName);
		if (relevantControl?.value !== control.value) {
			return {[ErrorCode.IDENTITY]: {required: relevantControl?.value}};
		}
		return null;
	}
}

export const exactLengthValidator = (exactLength: number): ValidatorFn => {
	return (control: AbstractControl): ValidationErrors | null => {
		const value = control.value;
		if (isNil(value) || value === '') {
			return null;
		}
		if (String(value).length !== exactLength) {
			return {[ErrorCode.EXACT_LENGTH]: {requiredLength: exactLength, actualLength: String(value).length}};
		}
		return null;
	};
}

export const regonValidator = (): ValidatorFn => {
	return (control: AbstractControl): ValidationErrors | null => {
		const value = control.value;
		if (isNil(value) || value === '') {
			return null;
		}
		const valueLength = String(value)?.length || 0;
		if (valueLength !== 9 && valueLength !== 14) {
			return {[ErrorCode.REGON]: true};
		}
		return null;
	}
}

export const atLeastOneFieldRequired = (...fieldNames: string[]): ValidatorFn => {
	return (form: AbstractControl): ValidationErrors | null => {
		const controls: AbstractControl[] = [];
		fieldNames.forEach(controlName => {
			if (!isNil(form.get(controlName))) {
				controls.push(form.get(controlName)!);
			}
		});
		if (controls.every(control => isNil(control.value))) {
			return {[ErrorCode.AT_LEAST_ONE_REQUIRED]: true};
		}
		return null;
	}
}

export function futureDateValidator(): ValidatorFn {
	return (control: AbstractControl): ValidationErrors | null => {
		if (!control.value) {
			return null;
		}

		const inputDate = new Date(control.value);
		const today = new Date();

		today.setHours(0, 0, 0, 0);

		if (inputDate <= today) {
			return {[ErrorCode.FUTURE_DATE]: true};
		}

		return null;
	};
}

export function nonEmptyArrayValidator() {
	return (tagsInput: AbstractControl): ValidationErrors | null => {
		if ((<any[]>tagsInput.value).length > 0) {
			return { [ErrorCode.NON_EMPTY_ARRAY]: true };
		}
		return null;
	};
}
