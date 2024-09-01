import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from "rxjs";
import {AbstractControl, FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {identityValidator} from "app/shared/utils/validation/validators.fn";
import {ValidationService} from "app/shared/utils/validation/validation.service";
import {ErrorCode} from "app/shared/utils/validation/error.types";

@Component({
  selector: 'app-register-institution-form',
  templateUrl: './register-institution-form.component.html',
  styleUrl: './register-institution-form.component.scss'
})
export class RegisterInstitutionFormComponent implements OnInit, OnDestroy {

	@Input() isLoadingData = false;

	registerForm!: FormGroup;
	formSubmitted = false;
	readonly errorCode = ErrorCode;
	private _subscription = new Subscription();

	constructor(public validationService: ValidationService, private formBuilder: FormBuilder) {
	}

	ngOnInit() {
		this.initForm();
		this.makeSubscriptions();
	}

	ngOnDestroy() {
	}

	getControlErrorType(control: AbstractControl): string | null {
		return this.validationService.getControlErrorType(<FormControl>control);
	}

	validateAndSubmit() {
		this.formSubmitted = true;
		this.markControlsTouched();
	}

	private initForm() {
		this.registerForm = this.formBuilder.group({
			email: [null, [Validators.required, Validators.email]],
			repeatEmail: [null, [Validators.required, identityValidator('email')]],
			firstName: [null, [...this.validationService.getTextualValidators({required: true})]],
			lastName: [null, [...this.validationService.getTextualValidators({required: true})]],
			institutionId: [null, [Validators.required]]
		});
	}

	private markControlsTouched(): void {
		Object.keys(this.registerForm.controls).forEach(controlName => {
			this.registerForm.controls[controlName].markAsTouched();
		});
	}

	private makeSubscriptions() {
		this.handleEmailValueChange();
	}

	private handleEmailValueChange() {

	}
}
