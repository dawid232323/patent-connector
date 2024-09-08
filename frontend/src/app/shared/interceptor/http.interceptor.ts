import {HttpInterceptorFn, HttpRequest} from '@angular/common/http';
import {inject} from "@angular/core";
import {SecurityService} from "app/shared/service/security.service";
import {map, switchMap, take, withLatestFrom} from "rxjs";
import {AppEndpoints} from "app/shared/types/api.types";
import {environment} from "src/environments/environment";

// TODO extend for login
export const httpInterceptor: HttpInterceptorFn = (req, next) => {
	const securityService = inject(SecurityService);
	return securityService.isUserLoggedIn
		.pipe(
			take(1),
			map((isUserLoggedIn) => {
				req = prepareRequest(req, isUserLoggedIn, securityService.token);
				return req;
			}),
			switchMap((request) => {
				return next(request);
			})
		);
};

function prepareRequest(request: HttpRequest<any>, isLoggedIn: boolean, token: string | null): HttpRequest<any> {
	let req = request.clone();
	console.log(`is user logged in ${isLoggedIn}`);
	console.log(`is url auth ${isUrlAuthorised(request.url)}`)
	if (isLoggedIn && isUrlAuthorised(request.url)) {
		req = req.clone({
			setHeaders: {
				'Content-Type': 'application/json',
				'Authorization': `Bearer ${token}`
			}
		});
	} else {
		req = request.clone({
			setHeaders: {
				'Content-Type': 'application/json',
			}
		})
	}
	return req;
}

function isUrlAuthorised(url: string): boolean {
	console.log(url)
	return AppEndpoints.getExcludedEndpoints().every(endpoint => {
		return !url.startsWith(endpoint)
	});
}
