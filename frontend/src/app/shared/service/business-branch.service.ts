import {Injectable} from '@angular/core';
import {ApiService} from "app/shared/service/api.service";
import {Observable} from "rxjs";
import {BusinessBranch} from "app/shared/types/business-branch.types";
import {AppEndpoints} from "app/shared/types/api.types";

@Injectable({
	providedIn: 'root'
})
export class BusinessBranchService {

	constructor(private apiService: ApiService) {
	}

	getAllSectionBusinessBranches(): Observable<BusinessBranch[]> {
		return this.apiService.get<BusinessBranch[]>(AppEndpoints.BusinessBranchEndpoints.sectionBusinessBranches);
	}
}
