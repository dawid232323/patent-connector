import {CanActivateFn} from '@angular/router';
import {inject} from "@angular/core";
import {UserService} from "app/shared/service/user.service";
import {map} from "rxjs";
import {isNil} from "lodash";
import {UserRole} from "app/shared/types/user.types";

export const roleRequiredGuard: CanActivateFn = (route, state) => {
	const userService = inject(UserService);
	const rolesFromRoute: UserRole[] = route.data['roles'];
	return userService.getLoggedUserDetails()
		.pipe(map(user => {
			if (isNil(user)) {
				return false;
			}
			if (user.roles.indexOf(UserRole.ADMIN) !== -1) { // admin should have access to all app views
				return true;
			}
			return rolesFromRoute.every(role => user.roles.indexOf(role) !== -1)
		}));
};
