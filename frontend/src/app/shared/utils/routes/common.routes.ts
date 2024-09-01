import {Routes} from "@angular/router";
import {RegisterComponent} from "app/features/common/features/register/register.component";
import {
	RegisterResearchInstitutionWorkerComponent
} from "app/features/common/features/register-research-institution-worker/register-research-institution-worker.component";

export const COMMON_ROUTES: Routes = [
	{
		path: 'register',
		component: RegisterComponent
	},
	{
		path: 'register-research-institution-worker',
		component: RegisterResearchInstitutionWorkerComponent
	}
]
