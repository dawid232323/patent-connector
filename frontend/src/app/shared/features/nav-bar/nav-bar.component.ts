import {Component} from '@angular/core';
import {Router, RouterLink, UrlTree} from "@angular/router";
import {SecurityService} from "app/shared/service/security.service";
import {map, Observable} from "rxjs";
import {AsyncPipe, NgIf} from "@angular/common";
import {UserService} from "app/shared/service/user.service";
import {isNil} from "lodash";
import {UserRole} from "app/shared/types/user.types";

@Component({
  selector: 'app-nav-bar',
  standalone: true,
	imports: [RouterLink, AsyncPipe, NgIf],
  templateUrl: './nav-bar.component.html',
  styleUrl: './nav-bar.component.scss'
})
export class NavBarComponent {

	constructor(private securityService: SecurityService,
				private router: Router,
				private userService: UserService) {
	}

	logout() {
		this.securityService.logoutUser();
	}

	resolveLoginLink(): UrlTree {
		return this.router.createUrlTree(['login'], {queryParams: {next: this.router.url}});
	}

	get isUserLoggedIn$(): Observable<boolean> {
		return this.securityService.isUserLoggedIn;
	}

	get isUserResearchWorker$(): Observable<boolean> {
		return this.userService.getLoggedUserDetails()
			.pipe(map(user => {
				if (isNil(user)) {
					return false;
				}
				return user.roles.includes(UserRole.RESEARCH_WORKER);
			}));
	}
}
