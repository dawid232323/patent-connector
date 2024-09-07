import {Component} from '@angular/core';
import {CreateEntrepreneurDto} from "app/features/common/features/register/types/registration.types";
import {RegisterService} from "app/features/common/features/register/service/register.service";
import {
	loadingFromSideAnimation,
	loadingFromSideAnimationReversed
} from "app/shared/utils/animations/loading.animation";
import {User} from "app/shared/types/user.types";
import {ErrorDialogService} from "app/shared/dialog/error-dialog/service/error-dialog.service";
import {HttpErrorResponse} from "@angular/common/http";

@Component({
	selector: 'app-register',
	standalone: false,
	templateUrl: './register.component.html',
	styleUrl: './register.component.scss',
	animations: [loadingFromSideAnimation, loadingFromSideAnimationReversed]
})
export class RegisterComponent {

	isLoadingData = false;
	hasFinishedWithSuccess = false;
	registeredUserEmail?: string;

	constructor(private registerService: RegisterService, private errorService: ErrorDialogService) {
	}

	handleFormSubmit(registerDto: CreateEntrepreneurDto): void {
		this.isLoadingData = true;
		this.registerService.registerUser(registerDto).subscribe({
			next: (response) => this.onRegisterSuccess(response),
			error: (error: HttpErrorResponse) => this.onRegisterFail(error)
		});
	}

	private onRegisterSuccess(response: User) {
		this.registeredUserEmail = response.email;
		this.hasFinishedWithSuccess = true;
		this.isLoadingData = false;
	}

	private onRegisterFail(error: HttpErrorResponse) {
		this.isLoadingData = false;
		this.errorService.openHttpResponseError(error.error);
	}
}
