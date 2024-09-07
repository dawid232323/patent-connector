import {Component} from '@angular/core';
import {
	loadingFromSideAnimation,
	loadingFromSideAnimationReversed
} from "app/shared/utils/animations/loading.animation";
import {
	ResearchInstitutionWorkerCreateDto
} from "app/features/common/features/register-research-institution-worker/types/research-institution-registration.types";
import {User} from "app/shared/types/user.types";
import {
	InstitutionWorkerRegistrationService
} from "app/features/common/features/register-research-institution-worker/service/institution-worker-registration.service";
import {ErrorDialogService} from "app/shared/dialog/error-dialog/service/error-dialog.service";
import {HttpErrorResponse} from "@angular/common/http";

@Component({
	selector: 'app-register-research-institution-worker',
	templateUrl: './register-research-institution-worker.component.html',
	styleUrl: './register-research-institution-worker.component.scss',
	animations: [loadingFromSideAnimation, loadingFromSideAnimationReversed]
})
export class RegisterResearchInstitutionWorkerComponent {

	isLoadingData = false;
	hasFinishedWithSuccess = false;
	registeredUserEmail?: string;

	constructor(private registerService: InstitutionWorkerRegistrationService, private errorService: ErrorDialogService) {
	}

	handleFormSubmit(createDto: ResearchInstitutionWorkerCreateDto) {
		this.isLoadingData = true;
		this.registerService.registerResearchInstitutionWorker(createDto).subscribe({
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
