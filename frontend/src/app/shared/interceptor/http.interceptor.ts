import {HttpInterceptorFn, HttpRequest} from '@angular/common/http';
import {inject} from "@angular/core";
import {SecurityService} from "app/shared/service/security.service";
import {map, switchMap, take} from "rxjs";
import {AppEndpoints} from "app/shared/types/api.types";

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
	return AppEndpoints.getExcludedEndpoints().every(endpoint => {
		return !url.startsWith(endpoint)
	});
}
