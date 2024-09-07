import {ActivatedRouteSnapshot, CanActivateFn, RouterStateSnapshot} from '@angular/router';
import {isNil} from "lodash";

export const activateAccountGuard: CanActivateFn = (route: ActivatedRouteSnapshot, state: RouterStateSnapshot) => {
	return !isNil(route.queryParams['t']);
};
