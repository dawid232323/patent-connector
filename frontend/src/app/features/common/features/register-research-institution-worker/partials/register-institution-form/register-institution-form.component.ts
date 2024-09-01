import {Component, EventEmitter, Input, OnDestroy, OnInit, Output} from '@angular/core';
import {debounceTime, distinctUntilChanged, filter, Subscription, switchMap} from "rxjs";
import {AbstractControl, FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {identityValidator} from "app/shared/utils/validation/validators.fn";
import {ValidationService} from "app/shared/utils/validation/validation.service";
import {ErrorCode} from "app/shared/utils/validation/error.types";
import {
	InstitutionWorkerRegistrationService
} from "app/features/common/features/register-research-institution-worker/service/institution-worker-registration.service";
import {
	AvailableInstitutionResult,
	ResearchInstitutionRegisterPreview, ResearchInstitutionWorkerCreateDto
} from "app/features/common/features/register-research-institution-worker/types/research-institution-registration.types";
import {MatSelectChange} from "@angular/material/select";
import {isNil} from "lodash";

@Component({
	selector: 'app-register-institution-form',
	templateUrl: './register-institution-form.component.html',
	styleUrl: './register-institution-form.component.scss'
})
export class RegisterInstitutionFormComponent implements OnInit, OnDestroy {

	@Input() isLoadingData = false;

	@Output() formSubmit = new EventEmitter<ResearchInstitutionWorkerCreateDto>();

	registerForm!: FormGroup;
	formSubmitted = false;
	availableInstitutions: ResearchInstitutionRegisterPreview[] = [];
	availableInstitutionMode: AvailableInstitutionResult = AvailableInstitutionResult.NONE;
	readonly errorCode = ErrorCode;
	readonly availableInstitutionResult = AvailableInstitutionResult;
	private _subscription = new Subscription();

	constructor(public validationService: ValidationService,
				private formBuilder: FormBuilder,
				private registrationService: InstitutionWorkerRegistrationService) {
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
		if (this.registerForm.valid) {
			const finalDto = this.processFinalDto();
			this.formSubmit.emit(finalDto);
		}
	}

	getSelectedInstitutionName(): string | null {
		const selectedInstitution = this.availableInstitutions
			.find(institution => institution.id === this.registerForm.value['institutionId']);
		if (isNil(selectedInstitution)) {
			return null;
		}
		return `${selectedInstitution.name} ${selectedInstitution.address}`;
	}

	handleSelectInstitutionChange(event: MatSelectChange) {
		const selectedInstitution = this.availableInstitutions
			.find(institution => institution.id === this.registerForm.value['institutionId']);
		if (isNil(selectedInstitution)) {
			return;
		}
		this.registerForm.patchValue({institutionId: selectedInstitution.id});
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
		this._subscription.add(
			this.emailControl?.valueChanges.pipe(
				distinctUntilChanged(),
				filter((_) => this.emailControl!.valid),
				debounceTime(300),
				switchMap((email) => this.registrationService.findByWorkerEmail(email))
			).subscribe(this.processAvailableInstitutions.bind(this))
		);
	}

	private processAvailableInstitutions(availableInstitutions: ResearchInstitutionRegisterPreview[]) {
		this.availableInstitutions = availableInstitutions;
		if (availableInstitutions.length === 0) {
			this.availableInstitutionMode = AvailableInstitutionResult.NONE;
			return;
		}

		if (availableInstitutions.length === 1) {
			this.registerForm.patchValue({institutionId: availableInstitutions[0].id});
			this.availableInstitutionMode = AvailableInstitutionResult.SINGLE;
		} else {
			this.availableInstitutionMode = AvailableInstitutionResult.MULTIPLE;
		}
	}

	private processFinalDto(): ResearchInstitutionWorkerCreateDto {
		const formValue = this.registerForm.value;
		return {
			email: formValue['email'],
			firstName: formValue['firstName'],
			lastName: formValue['lastName'],
			institutionId: formValue['institutionId']
		};
	}

	get emailControl(): FormControl | null {
		return <FormControl>this.registerForm?.get('email') || null;
	}
}
