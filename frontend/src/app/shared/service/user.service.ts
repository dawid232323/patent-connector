import {Injectable} from '@angular/core';
import {ApiService} from "app/shared/service/api.service";
import {SecurityService} from "app/shared/service/security.service";
import {map, Observable, of, switchMap, tap} from "rxjs";
import {User} from "app/shared/types/user.types";
import {LoginDto, TokenDto} from "app/shared/types/security.types";
import {AppEndpoints} from "app/shared/types/api.types";
import {isNil} from "lodash";
import {HttpEvent, HttpResponse} from "@angular/common/http";

@Injectable({
	providedIn: 'root'
})
export class UserService {

	private _loggedUserDetails: User | null = null;

	constructor(private apiService: ApiService, private securityService: SecurityService) {
	}

	loginUser(email: string, password: string): Observable<User> {
		const loginDto = this.resolveLoginDto(email, password);
		return this.apiService.post(loginDto, AppEndpoints.SecurityEndpoints.login)
			.pipe(
				map((tokenDto: TokenDto) => this.securityService.loginUser(tokenDto)),
				switchMap(() => <Observable<User>>this.getLoggedUserDetails())
			);
	}

	refreshToken(): Observable<TokenDto> {
		const refreshToken = this.securityService.refreshToken;
		return this.apiService.post({}, AppEndpoints.SecurityEndpoints.refreshToken.concat(`/${refreshToken}`))
			.pipe(
				map((tokenDto: TokenDto) => this.securityService.refreshUserToken(tokenDto))
			);
	}

	getLoggedUserDetails(): Observable<User | null> {
		return this.securityService.isUserLoggedIn
			.pipe(
				switchMap((isUserLoggedIn) => isUserLoggedIn ? this.resolveUserDetails() : of(null))
			);
	}

	updateSelectedBusinessBranches(userId: number, selectedBranches: number[]): Observable<User> {
		const url = `${AppEndpoints.UserEndpoints.updateBusinessBranches}/${userId}`;
		return this.apiService.put<number[], User>(url, selectedBranches);
	}

	private resolveUserDetails(): Observable<User> {
		if (!isNil(this._loggedUserDetails)) {
			return of(this._loggedUserDetails);
		}
		return this.apiService.get(AppEndpoints.UserEndpoints.myDetails)
			.pipe(map(user => {
				this._loggedUserDetails = user;
				return user;
			}));
	}

	private resolveLoginDto(email: string, password: string): LoginDto {
		return {email, password};
	}
}
