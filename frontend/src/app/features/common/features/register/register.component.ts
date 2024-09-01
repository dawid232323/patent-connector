import {Component} from '@angular/core';
import {CreateEntrepreneurDto} from "app/features/common/features/register/types/registration.types";
import {RegisterService} from "app/features/common/features/register/service/register.service";
import {HttpEvent, HttpResponse} from "@angular/common/http";
import {
	loadingFromSideAnimation,
	loadingFromSideAnimationReversed
} from "app/shared/utils/animations/loading.animation";
import {User} from "app/shared/types/user.types";

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

	constructor(private registerService: RegisterService) {
	}

	handleFormSubmit(registerDto: CreateEntrepreneurDto): void {
		this.isLoadingData = true;
		this.registerService.registerUser(registerDto).subscribe({
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
