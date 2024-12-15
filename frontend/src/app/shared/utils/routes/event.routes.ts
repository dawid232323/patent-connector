import {Routes} from "@angular/router";
import {AddEditEventComponent} from "app/features/event/add-edit-event/add-edit-event.component";
import {roleRequiredGuard} from "app/shared/guard/role-required.guard";
import {UserRole} from "app/shared/types/user.types";
import {eventResolver} from "app/features/event/resolver/event.resolver";
import {authenticatedGuard} from "app/shared/guard/authenticated.guard";
import {FormUsageMode} from "app/shared/types/util.types";
import {specificBusinessBranchesResolver} from "app/features/event/resolver/specific-business-branches.resolver";

export const EVENT_ROUTES: Routes = [
	{
		path: 'events',
		children: [
			{
				path: 'add',
				component: AddEditEventComponent,
				canActivate: [authenticatedGuard, roleRequiredGuard],
				data: {
					roles: [UserRole.RESEARCH_WORKER],
					mode: FormUsageMode.CREATE
				},
				resolve: {
					businessBranches: specificBusinessBranchesResolver
				}
			},
			{
				path: 'edit/:id',
				component: AddEditEventComponent,
				canActivate: [authenticatedGuard, roleRequiredGuard],
				data: {
					roles: [UserRole.RESEARCH_WORKER],
					mode: FormUsageMode.EDIT
				},
				resolve: {
					event: eventResolver
				}
			}
		]
	}
]
