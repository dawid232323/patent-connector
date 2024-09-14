import {Routes} from "@angular/router";
import {RegisterComponent} from "app/features/common/features/register/register.component";
import {
	RegisterResearchInstitutionWorkerComponent
} from "app/features/common/features/register-research-institution-worker/register-research-institution-worker.component";
import {ActivateAccountComponent} from "app/features/common/features/activate-account/activate-account.component";
import {activateAccountGuard} from "app/features/common/features/activate-account/guard/activate-account.guard";
import {LoginComponent} from "app/features/common/features/login/login.component";
import {
	SelectBusinessBranchesComponent
} from "app/features/common/features/select-business-branches/select-business-branches.component";
import {authenticatedGuard} from "app/shared/guard/authenticated.guard";
import {roleRequiredGuard} from "app/shared/guard/role-required.guard";
import {UserRole} from "app/shared/types/user.types";
import {
	selectBusinessBranchesResolver
} from "app/features/common/features/select-business-branches/resolver/select-business-branches.resolver";

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
	},
	{
		path: 'login',
		component: LoginComponent
	},
	{
		path: 'select-business-branches',
		component: SelectBusinessBranchesComponent,
		canActivate: [
			authenticatedGuard,
			roleRequiredGuard
		],
		resolve: {
			businessBranches: selectBusinessBranchesResolver
		},
		data: {
			roles: [UserRole.ENTREPRENEUR]
		}
	}
]
