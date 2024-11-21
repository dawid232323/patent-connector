import { Component } from '@angular/core';
import {ActivatedRoute, Router, RouterLink, UrlTree} from "@angular/router";
import {SecurityService} from "app/shared/service/security.service";
import {Observable} from "rxjs";
import {AsyncPipe, NgIf} from "@angular/common";

@Component({
  selector: 'app-nav-bar',
  standalone: true,
	imports: [RouterLink, AsyncPipe, NgIf],
  templateUrl: './nav-bar.component.html',
  styleUrl: './nav-bar.component.scss'
})
export class NavBarComponent {

	constructor(private securityService: SecurityService, private router: Router) {
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
}
