import {Injectable} from '@angular/core';
import {ApiService} from "app/shared/service/api.service";
import {PatentSearchQuery, PatentSearchResult} from "app/shared/types/patent.types";
import {Observable, of} from "rxjs";
import {AppEndpoints, PagedResult} from "app/shared/types/api.types";

@Injectable()
export class PatentListingService {

	constructor(private apiService: ApiService) {
	}

	getQueriedPatents(searchParams: PatentSearchQuery): Observable<PagedResult<PatentSearchResult>> {
		return this.apiService.get(AppEndpoints.PatentEndpoints.patentsSearch, {params: searchParams});
	}
}
