import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable, ReplaySubject} from "rxjs";
import {TokenDto} from "app/shared/types/security.types";
import {isNil} from "lodash";

@Injectable({
	providedIn: 'root'
})
export class SecurityService {

	private _isUSerLoggedIn = new BehaviorSubject<boolean>(false);
	private _token: string | null = null;
	private _refreshToken: string | null = null;

	private readonly TOKEN_KEY: string = 'TOKEN';
	private readonly REFRESH_TOKEN_KEY: string = 'REFRESH_TOKEN';

	constructor() {
		this._isUSerLoggedIn.next(false);
		this.setTokenFromStorage();
	}

	loginUser(tokenDto: TokenDto) {
		this.setTokenPair(tokenDto);
		this._isUSerLoggedIn.next(true);
	}

	logoutUser() {
		this.resetTokenPair();
		this._isUSerLoggedIn.next(false);
	}

	private setTokenPair(tokenDto: TokenDto) {
		this._token = tokenDto.token;
		this._refreshToken = tokenDto.refreshToken;

		sessionStorage.setItem(this.TOKEN_KEY, tokenDto.token);
		sessionStorage.setItem(this.REFRESH_TOKEN_KEY, tokenDto.refreshToken);
	}

	private setTokenFromStorage() {
		this._token = sessionStorage.getItem(this.TOKEN_KEY);
		this._refreshToken = sessionStorage.getItem(this.REFRESH_TOKEN_KEY);
		if (!isNil(this._token) && !isNil(this._refreshToken)) {
			this._isUSerLoggedIn.next(true);
		}
	}

	private resetTokenPair() {
		this._token = null;
		this._refreshToken = null;
	}

	get token(): string | null {
		return this._token;
	}

	get refreshToken(): string | null {
		return this._refreshToken;
	}

	get isUserLoggedIn(): Observable<boolean> {
		return this._isUSerLoggedIn.asObservable();
	}
}
