import {Component} from '@angular/core';
import {
	loadingFromSideAnimation,
	loadingFromSideAnimationReversed
} from "app/shared/utils/animations/loading.animation";
import {
	ResearchInstitutionWorkerCreateDto
} from "app/features/common/features/register-research-institution-worker/types/research-institution-registration.types";
import {User} from "app/shared/types/user.types";
import {RegisterService} from "app/features/common/features/register/service/register.service";
import {
	InstitutionWorkerRegistrationService
} from "app/features/common/features/register-research-institution-worker/service/institution-worker-registration.service";

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

	constructor(private registerService: InstitutionWorkerRegistrationService) {
	}

	handleFormSubmit(createDto: ResearchInstitutionWorkerCreateDto) {
		this.isLoadingData = true;
		this.registerService.registerResearchInstitutionWorker(createDto).subscribe({
			next: (response) => this.onRegisterSuccess(response),
			error: this.onRegisterFail.bind(this)
		});
	}

	private onRegisterSuccess(response: User) {
		this.registeredUserEmail = response.email;
		this.hasFinishedWithSuccess = true;
		this.isLoadingData = false;
	}

	private onRegisterFail() {
		this.isLoadingData = false;
	}
}
