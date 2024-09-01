import {Component} from '@angular/core';

@Component({
	selector: 'app-register-research-institution-worker',
	templateUrl: './register-research-institution-worker.component.html',
	styleUrl: './register-research-institution-worker.component.scss'
})
export class RegisterResearchInstitutionWorkerComponent {

	isLoadingData = false;
	hasFinishedWithSuccess = false;
	registeredUserEmail?: string;

}
