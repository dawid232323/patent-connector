import {Injectable} from '@angular/core';
import {
	Resolve,
	RouterStateSnapshot,
	ActivatedRouteSnapshot
} from '@angular/router';
import {Observable, of} from 'rxjs';
import {BusinessBranchService} from "app/shared/service/business-branch.service";
import {BusinessBranch} from "app/shared/types/business-branch.types";

@Injectable({
	providedIn: 'root'
})
export class SectionBusinessBranchesResolver implements Resolve<BusinessBranch[]> {

	constructor(private businessBranchService: BusinessBranchService) {
	}

	resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<BusinessBranch[]> {
		return this.businessBranchService.getAllSectionBusinessBranches();
	}
}
