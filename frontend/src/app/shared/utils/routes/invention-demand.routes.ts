import {Routes} from "@angular/router";
import {
	AddInventionDemandComponent
} from "app/features/invention-demand/features/add-invention-demand/add-invention-demand.component";
import {authenticatedGuard} from "app/shared/guard/authenticated.guard";
import {roleRequiredGuard} from "app/shared/guard/role-required.guard";
import {UserRole} from "app/shared/types/user.types";
import {loggedUserResolver} from "app/shared/resolver/logged-user.resolver";
import {
	researchInstitutionWorkersResolver
} from "app/features/invention-demand/features/add-invention-demand/resolver/research-institution-workers.resolver";
import {specificBusinessBranchesResolver} from "app/features/event/resolver/specific-business-branches.resolver";

export const INVENTION_DEMAND_ROUTES: Routes = [
	{
		path: 'add-invention-demand',
		component: AddInventionDemandComponent,
		canActivate: [authenticatedGuard, roleRequiredGuard],
		data: {
			roles: [UserRole.ENTREPRENEUR]
		},
		resolve: {
			businessBranches: specificBusinessBranchesResolver,
			loggedUser: loggedUserResolver,
			institutionWorkers: researchInstitutionWorkersResolver
		}
	}
]
