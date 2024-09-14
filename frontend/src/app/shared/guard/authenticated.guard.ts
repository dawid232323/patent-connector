import {CanActivateFn} from '@angular/router';
import {inject} from "@angular/core";
import {SecurityService} from "app/shared/service/security.service";

export const authenticatedGuard: CanActivateFn = (route, state) => {
	const securityService = inject(SecurityService);
	return securityService.isUserLoggedIn;
};
