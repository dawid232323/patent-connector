import {Injectable} from '@angular/core';
import {ApiService} from "app/shared/service/api.service";
import {map, Observable, of, tap} from "rxjs";
import {BusinessBranch} from "app/shared/types/business-branch.types";
import {AppEndpoints} from "app/shared/types/api.types";
import {isNil} from "lodash";

@Injectable({
	providedIn: 'root'
})
export class BusinessBranchService {

	private _sectionBranches?: BusinessBranch[];
	private _specificBranches?: BusinessBranch[];

	constructor(private apiService: ApiService) {
	}

	getAllSectionBusinessBranches(): Observable<BusinessBranch[]> {
		if (!isNil(this._sectionBranches)) {
			return of(this._sectionBranches);
		}
		return this.apiService.get<BusinessBranch[]>(AppEndpoints.BusinessBranchEndpoints.sectionBusinessBranches)
			.pipe(map(branches => {
				this._sectionBranches = branches;
				return this._sectionBranches!;
			}));
	}

	getAllSpecificBusinessBranches(): Observable<BusinessBranch[]> {
		if (!isNil(this._specificBranches)) {
			return of(this._specificBranches);
		}
		return this.apiService.get<BusinessBranch[]>(AppEndpoints.BusinessBranchEndpoints.specificBusinessBranches)
			.pipe(map(branches => {
				this._specificBranches = branches;
				return this._specificBranches!;
			}));
	}

	getBusinessBranchChildren(parentId: number): Observable<BusinessBranch[]> {
		return this.apiService.get<BusinessBranch[]>(AppEndpoints.BusinessBranchEndpoints.searchBranchChildren.concat(`/${parentId}`));
	}
}
