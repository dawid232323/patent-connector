import {Injectable} from '@angular/core';
import {UserService} from "app/shared/service/user.service";
import {PatentSearchQuery} from "app/shared/types/patent.types";
import {Observable, of, switchMap} from "rxjs";
import {isNil} from "lodash";
import {User} from "app/shared/types/user.types";

@Injectable()
export class PatentListingParamsService {

	private _queryParamsState?: PatentSearchQuery;
	private readonly _defaultState: PatentSearchQuery = {
		number: 0,
		size: 10,
		businessBranchesIds: []
	}

	constructor(private userService: UserService) {
	}

	retrieveQueryParams(): Observable<PatentSearchQuery> {
		if (isNil(this._queryParamsState)) {
			return this.retrieveForInitial();
		}
		return this.retrieveExisting();
	}

	updateSearchParams(params: Partial<PatentSearchQuery>) {
		if (isNil(params)) {
			return;
		}
		if (isNil(this._queryParamsState)) {
			this._queryParamsState = {...this._defaultState, ...params};
		}
		this._queryParamsState = {...this._queryParamsState!, ...params};
	}

	private retrieveForInitial(): Observable<PatentSearchQuery> {
		return this.userService.getLoggedUserDetails()
			.pipe(switchMap(loggedUser => isNil(loggedUser) ? of(this._defaultState) : this.resolveForLoggedUser(loggedUser)));
	}

	private retrieveExisting(): Observable<PatentSearchQuery> {
		return of(this._queryParamsState!);
	}

	private resolveForLoggedUser(loggedUser: User): Observable<PatentSearchQuery> {
		if (isNil(loggedUser.entrepreneursData)) {
			return of(this._defaultState);
		}
		const branchesIds = loggedUser.entrepreneursData.interestBranches.map(branch => branch.id);
		return of({
			...this._defaultState,
			businessBranchesIds: branchesIds
		});
	}
}
