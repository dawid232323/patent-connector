import {Component, OnInit} from '@angular/core';
import {AbstractControl, FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {identityValidator} from "app/shared/utils/validation/validators.fn";
import {ValidationService} from "app/shared/utils/validation/validation.service";
import {
	loadingFromSideAnimation,
	loadingFromSideAnimationReversed
} from "app/shared/utils/animations/loading.animation";
import {ActivatedRoute} from "@angular/router";
import {ErrorCode} from "app/shared/utils/validation/error.types";
import {ActivateAccountService} from "app/features/common/features/activate-account/service/activate-account.service";
import {ErrorDialogService} from "app/shared/dialog/error-dialog/service/error-dialog.service";
import {HttpErrorResponse} from "@angular/common/http";

@Component({
	selector: 'app-activate-account',
	templateUrl: './activate-account.component.html',
	styleUrl: './activate-account.component.scss',
	animations: [loadingFromSideAnimation, loadingFromSideAnimationReversed]
})
export class ActivateAccountComponent implements OnInit {

	setPasswordForm!: FormGroup;
	isLoadingData = false;
	hasFinishedWithSuccess = false;

	readonly errorCode = ErrorCode;

	private _registrationToken!: string;

	constructor(private formBuilder: FormBuilder,
				private activatedRoute: ActivatedRoute,
				private activateAccService: ActivateAccountService,
				private errorService: ErrorDialogService,
				public validationService: ValidationService) {
	}

	ngOnInit() {
		this.setRegistrationToken();
		this.createForm();
	}

	getControlErrorType(control: AbstractControl): string | null {
		return this.validationService.getControlErrorType(<FormControl>control);
	}

	submitPassword() {
		if (this.setPasswordForm.invalid) {
			return;
		}
		this.isLoadingData = true;
		this.activateAccService.activateAccount(this.passwordValue, this._registrationToken).subscribe({
			next: () => this.handleActivationSuccess(),
			error: err => this.handleActivationError(err)
		});
	}

	private createForm() {
		this.setPasswordForm = this.formBuilder.group({
			password: [null, [Validators.required, Validators.pattern(/^(?=.*[A-Z])(?=.*\d)[A-Za-z0-9]{8,}$/)]],
			repeatPassword: [null, [Validators.required, identityValidator('password')]]
		});
	}

	private handleActivationSuccess() {
		this.isLoadingData = false;
		this.hasFinishedWithSuccess = true;
	}

	private handleActivationError(error: HttpErrorResponse) {
		this.isLoadingData = false;
		this.errorService.openDefaultErrorResponse(error);
	}

	private setRegistrationToken() {
		this._registrationToken = this.activatedRoute.snapshot.queryParams['t'];
	}

	get hasRequiredLength(): boolean {
		return this.passwordValue?.length >= 8;
	}

	get hasAtLeastOneCapitalChar(): boolean {
		return /[A-Z]/.test(this.passwordValue);
	}

	get hasAtLeastOneNumber(): boolean {
		return /\d/.test(this.passwordValue);
	}

	get hasNoSpecialCharacters(): boolean {
		return /^[A-Za-z0-9]+$/.test(this.passwordValue);
	}

	get passwordsMatch(): boolean {
		return this.passwordValue === this.repeatPasswordValue;
	}

	get passwordValue(): string {
		return this.setPasswordForm?.value['password'] || '';
	}

	get repeatPasswordValue(): string {
		return this.setPasswordForm?.value['repeatPassword'] || '';
	}
}
