import {Component, EventEmitter, OnDestroy, OnInit, Output} from '@angular/core';
import {AbstractControl, FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {Subscription} from "rxjs";
import {ValidationService} from "app/shared/utils/validation/validation.service";
import {ErrorCode} from "app/shared/utils/validation/error.types";
import {
	atLeastOneFieldRequired,
	exactLengthValidator,
	identityValidator,
	regonValidator
} from "app/shared/utils/validation/validators.fn";
import {
	CreateEntrepreneurDto,
	CreateEntrepreneursDataDto
} from "app/features/common/features/register/types/registration.types";

@Component({
  selector: 'app-register-form',
  templateUrl: './register-form.component.html',
  styleUrl: './register-form.component.scss'
})
export class RegisterFormComponent implements OnInit, OnDestroy {

	@Output() formSubmit = new EventEmitter<CreateEntrepreneurDto>();

	registerForm!: FormGroup;
	formSubmitted= false;
	readonly errorCode = ErrorCode;

	private _subscription: Subscription = new Subscription();

	constructor(private formBuilder: FormBuilder, public validationService: ValidationService) {
	}

	ngOnInit() {
		this.registerForm = this.createForm();
		this.addAdditionalValidators();
	}

	ngOnDestroy() {
		this._subscription.unsubscribe();
	}

	getControlErrorType(control: AbstractControl): string | null {
		return this.validationService.getControlErrorType(<FormControl>control);
	}

	validateAndSubmit(): void {
		this.markControlsTouched();
		this.formSubmitted = true;
		if (this.registerForm.valid) {
			const createDto = this.resolveCreateDto();
			this.formSubmit.emit(createDto);
		}
	}

	private createForm(): FormGroup {
		return this.formBuilder.group({
			email: [null, [Validators.required, Validators.email]],
			repeatEmail: [null, [Validators.required, identityValidator('email')]],
			firstName: [null, [...this.validationService.getTextualValidators({required: true})]],
			lastName: [null, [...this.validationService.getTextualValidators({required: true})]],
			nip: [null, [exactLengthValidator(10)]],
			regon: [null, [regonValidator()]],
			companyName: [null, [...this.validationService.getTextualValidators()]]
		}, {validators: [atLeastOneFieldRequired('nip', 'regon')]});
	}

	private addAdditionalValidators(): void {
		this.registerForm.get('repeatEmail')?.addValidators(identityValidator('email'));
		this.registerForm.get('repeatEmail')?.updateValueAndValidity();
	}

	private markControlsTouched(): void {
		Object.keys(this.registerForm.controls).forEach(controlName => {
			this.registerForm.controls[controlName].markAsTouched();
		});
	}

	private resolveCreateDto(): CreateEntrepreneurDto {
		const formValue = this.registerForm.value;
		return {
			email: formValue['email'],
			firstName: formValue['firstName'],
			lastName: formValue['lastName'],
			entrepreneursData: {
				nip: formValue['nip'] || null,
				regon: formValue['regon'] || null,
				companyName: formValue['companyName'] || null
			}
		};
	}
}
