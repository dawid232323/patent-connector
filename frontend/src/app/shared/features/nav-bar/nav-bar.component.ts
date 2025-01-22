import {Component, OnDestroy, OnInit} from '@angular/core';
import {Router, RouterLink, UrlTree} from "@angular/router";
import {SecurityService} from "app/shared/service/security.service";
import {Subscription} from "rxjs";
import {NgIf} from "@angular/common";
import {UserService} from "app/shared/service/user.service";
import {isNil} from "lodash";
import {User, UserRole} from "app/shared/types/user.types";

@Component({
  selector: 'app-nav-bar',
  standalone: true,
	imports: [RouterLink, NgIf],
  templateUrl: './nav-bar.component.html',
  styleUrl: './nav-bar.component.scss'
})
export class NavBarComponent implements OnInit, OnDestroy {

	private _user: User | null = null;
	private _userLoggedIn = false;
	private _subscription: Subscription = new Subscription();

	constructor(private securityService: SecurityService,
				private router: Router,
				private userService: UserService) {
	}

	ngOnInit() {
		this._subscription.add(this.securityService.isUserLoggedIn.subscribe(this.handleUserLoginChange.bind(this)));
		// this.loadUserData();
	}

	ngOnDestroy(): void {
		this._subscription.unsubscribe();
	}

	logout() {
		this.userService.logoutUser();
		this.router.navigate(['patents', 'listing']);
	}

	resolveLoginLink(): UrlTree {
		return this.router.createUrlTree(['login'], {queryParams: {next: this.router.url}});
	}

	private loadUserData() {
		this.userService.getLoggedUserDetails().subscribe(user => {
			this._user = user;
		});
	}

	private handleUserLoginChange(isUserLoggedIn: boolean) {
		this._userLoggedIn = isUserLoggedIn;
		if (!isUserLoggedIn) {
			this._user = null;
		} else {
			this.loadUserData();
		}
	}

	get isUserLoggedIn(): boolean {
		return this._userLoggedIn;
	}

	get isUserResearchWorker(): boolean {
		if (isNil(this._user)) {
			return false;
		}
		return this._user.roles.includes(UserRole.RESEARCH_WORKER);
	}

	get isUserEntrepreneur(): boolean {
		if (isNil(this._user)) {
			return false;
		}
		return this._user.roles.includes(UserRole.ENTREPRENEUR);
	}
}
