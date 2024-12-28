import {Injectable} from '@angular/core';
import {AbstractControl, FormControl, ValidatorFn, Validators} from "@angular/forms";
import {ErrorCode, TextualValidationOptions} from "app/shared/utils/validation/error.types";
import {isNil} from "lodash";

@Injectable({
	providedIn: 'root'
})
export class ValidationService {

	readonly defaultMaxLength: number = 300;
	readonly defaultMinLength: number = 1;

	constructor() {
	}

	getTextualValidators(options?: TextualValidationOptions): ValidatorFn[] {
		const baseValidators = [
			Validators.maxLength(options?.maxLength || this.defaultMaxLength),
			Validators.minLength(options?.minLength || this.defaultMinLength),
			Validators.pattern(/^[\p{L}\p{N}\p{P}\s]*$/u)
		]
		if (options?.required === true) {
			return [...baseValidators, Validators.required];
		}
		return baseValidators;
	}

	getControlErrorType(control: FormControl): string | null {
		if (control.untouched) {
			return null;
		}
		if (control.hasError(ErrorCode.REQUIRED)) {
			return ErrorCode.REQUIRED;
		}
		if (control.hasError(ErrorCode.IDENTITY)) {
			return ErrorCode.IDENTITY;
		}
		if (control.hasError(ErrorCode.REGON)) {
			return ErrorCode.REGON;
		}
		if (control.hasError(ErrorCode.EMAIL)) {
			return ErrorCode.EMAIL;
		}
		if (control.hasError(ErrorCode.MAX_LENGTH)) {
			return ErrorCode.MAX_LENGTH;
		}
		if (control.hasError(ErrorCode.MIN_LENGTH)) {
			return ErrorCode.MIN_LENGTH;
		}
		if (control.hasError(ErrorCode.EXACT_LENGTH)) {
			return ErrorCode.EXACT_LENGTH;
		}
		if (control.hasError(ErrorCode.FUTURE_DATE)) {
			return ErrorCode.FUTURE_DATE;
		}
		if (control.hasError(ErrorCode.ARRAY_PRESENT)) {
			return ErrorCode.ARRAY_PRESENT;
		}
		return null;
	}

	controlHasError(control: AbstractControl, errorCode: string): boolean {
		if (isNil(control.errors)) {
			return false;
		}
		return control.hasError(errorCode);
	}

	getMaxLengthMessage(control: AbstractControl): string | null {
		if (!this.controlHasError(control, ErrorCode.MAX_LENGTH)) {
			return null;
		}
		const error = control.errors![ErrorCode.MAX_LENGTH];
		return `Pole powinno być krótsze niż ${error.requiredLength}. Aktualna długość: ${error.actualLength}`;
	}

	getMinLengthMessage(control: AbstractControl): string | null {
		if (!this.controlHasError(control, ErrorCode.MIN_LENGTH)) {
			return null;
		}
		const error = control.errors![ErrorCode.MIN_LENGTH];
		return `Pole powinno być dłuższe niż ${error.requiredLength}. Aktualna długość: ${error.actualLength}`;
	}

	getExactLengthMessage(control: AbstractControl): string | null {
		if (!this.controlHasError(control, ErrorCode.EXACT_LENGTH)) {
			return null;
		}
		const error = control.errors![ErrorCode.EXACT_LENGTH];
		return `Pole mieć długość ${error.requiredLength}. Aktualna długość: ${error.actualLength}`;
	}
}
