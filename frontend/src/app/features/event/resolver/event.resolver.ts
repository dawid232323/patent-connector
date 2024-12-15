import {ActivatedRouteSnapshot, ResolveFn, RouterStateSnapshot} from '@angular/router';

export const eventResolver: ResolveFn<boolean> = (route: ActivatedRouteSnapshot, state: RouterStateSnapshot) => {
  return true;
};
