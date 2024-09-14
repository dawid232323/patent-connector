import {Component, OnInit} from '@angular/core';
import {
	loadingFromSideAnimation,
	loadingFromSideAnimationReversed
} from "app/shared/utils/animations/loading.animation";
import {AbstractControl, FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {ValidationService} from "app/shared/utils/validation/validation.service";
import {ErrorCode} from "app/shared/utils/validation/error.types";
import {UserService} from "app/shared/service/user.service";
import {User, UserRole} from "app/shared/types/user.types";
import {ActivatedRoute, Router} from "@angular/router";
import {isNil} from "lodash";

@Component({
	selector: 'app-login',
	templateUrl: './login.component.html',
	styleUrl: './login.component.scss',
	animations: [loadingFromSideAnimation, loadingFromSideAnimationReversed]
})
export class LoginComponent implements OnInit {

	loginForm!: FormGroup;
	isLoadingData = false;

	static readonly NEXT_QUERY_PARAM: string = 'next';

	constructor(private formBuilder: FormBuilder,
				public validationService: ValidationService,
				private userService: UserService,
				private router: Router,
				private activatedRoute: ActivatedRoute) {
	}

	ngOnInit() {
		this.createForm();
	}

	getControlErrorType(control: AbstractControl): string | null {
		return this.validationService.getControlErrorType(<FormControl>control);
	}

	performLogin() {
		if (this.loginForm.invalid) {
			return;
		}
		this.userService.loginUser(this.loginForm.value['email'], this.loginForm.value['password'])
			.subscribe({
				next: (user) => this.handleLoginSuccess(user)
			});
	}

	private createForm() {
		this.loginForm = this.formBuilder.group({
			email: [null, [Validators.required, Validators.email]],
			password: [null, [Validators.required]]
		});
	}

	private handleLoginSuccess(user: User) {
		if (this.shouldRedirectToInterestCollection(user)) {
			return this.router.navigate(['/select-business-branches']);
		}
		if (this.shouldNotRedirectToDefault()) {
			return this.router.navigate(this.activatedRoute.snapshot.queryParams[LoginComponent.NEXT_QUERY_PARAM]);
		}
		return this.router.navigate(['/']);
	}

	private shouldNotRedirectToDefault(): boolean {
		return !isNil(this.activatedRoute.snapshot.queryParams[LoginComponent.NEXT_QUERY_PARAM]);
	}

	private shouldRedirectToInterestCollection(user: User): boolean {
		return user.roles.includes(UserRole.ENTREPRENEUR) && user.entrepreneursData?.interestBranches?.length === 0;
	}

	protected readonly errorCode = ErrorCode;
}
