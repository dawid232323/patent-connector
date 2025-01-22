import {
	HttpErrorResponse,
	HttpEvent,
	HttpHandler,
	HttpInterceptor,
	HttpRequest,
	HttpResponse,
} from '@angular/common/http';
import {
	catchError,
	firstValueFrom,
	map,
	Observable,
	of,
	Subject,
	switchMap,
	take,
	throwError,
	withLatestFrom,
} from 'rxjs';
import {Injectable} from '@angular/core';
import {Router} from '@angular/router';
import {SecurityService} from "app/shared/service/security.service";
import {AppEndpoints} from "app/shared/types/api.types";
import {UserService} from "app/shared/service/user.service";
import {isNil} from "lodash";

@Injectable({providedIn: 'root'})
export class HttpErrorInterceptor implements HttpInterceptor {
	private isRefreshing = false;
	private tokenHasBeenRefreshed = new Subject<void>();

	constructor(
		private authService: SecurityService,
		private userService: UserService,
		private router: Router
	) {
	}

	intercept(
		req: HttpRequest<any>,
		next: HttpHandler
	): Observable<HttpEvent<any>> {
		return next.handle(req).pipe(
			catchError((requestError) => {
				const isUserLoggedIn = !isNil(this.authService.token) && !isNil(this.authService.refreshToken);
				if (
					requestError instanceof HttpErrorResponse &&
					!req.url.includes('/login') &&
					(requestError.status === 401 || requestError.status === 403)
				) {
					return this.handleUnauthorized(req, next, isUserLoggedIn);
				}
				return throwError(() => requestError);
			}),
		)

	}

	private handleUnauthorized(req: HttpRequest<any>, next: HttpHandler, isUserLoggedIn: boolean): Observable<HttpEvent<any>> {
		// when current request is the one to refresh access token
		if (this.isRefreshing && req.url.includes(AppEndpoints.SecurityEndpoints.refreshToken)) {
			if (isNil(this.authService.refreshToken) || this.authService.refreshToken.trim() === '') {
				this.router.navigate(['/login'], {
					queryParams: {
						next: this.router.url
					}
				});
				return of(new HttpResponse());
			}
			return next.handle(req);
		}
		// when it is the first request that fails
		if (isUserLoggedIn && !this.isRefreshing) {
			this.isRefreshing = true;
			return this.userService.refreshToken().pipe(
				switchMap(() => {
					this.isRefreshing = false;
					this.tokenHasBeenRefreshed.next();
					// old request doesn't fall into AuthorizationInterceptor once again so new token needs to be added manually
					return this.getRefreshedRequest(req, next);
				}),
				catchError((error, caught) => {
					this.isRefreshing = false;
					this.userService.logoutUser();
					this.router.navigate(['/login'], {
						queryParams: {next: this.router.url},
					});
					return caught;
				})
			);
		}
		// when there are multiple request at one time (e.g. via forkJoin) and token is already refreshing
		return this.tokenHasBeenRefreshed.pipe(
			take(1),
			switchMap(() => {
				return this.getRefreshedRequest(req, next);
			})
		);
	}

	private getRefreshedRequest(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
		const refreshedRequest = req.clone({
			setHeaders: {
				Authorization: `Bearer ${this.authService.token}`,
			},
		});
		return next.handle(refreshedRequest);
	}
}
