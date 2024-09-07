import {Routes} from "@angular/router";
import {RegisterComponent} from "app/features/common/features/register/register.component";
import {
	RegisterResearchInstitutionWorkerComponent
} from "app/features/common/features/register-research-institution-worker/register-research-institution-worker.component";
import {ActivateAccountComponent} from "app/features/common/features/activate-account/activate-account.component";
import {activateAccountGuard} from "app/features/common/features/activate-account/guard/activate-account.guard";

export const COMMON_ROUTES: Routes = [
	{
		path: 'register',
		component: RegisterComponent
	},
	{
		path: 'register-research-institution-worker',
		component: RegisterResearchInstitutionWorkerComponent
	},
	{
		path: 'activate-account',
		component: ActivateAccountComponent,
		canActivate: [
			activateAccountGuard
		]
	}
]
