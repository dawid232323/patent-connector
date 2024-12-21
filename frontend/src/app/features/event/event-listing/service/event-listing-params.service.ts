import {Injectable} from '@angular/core';
import {EventSearchQuery} from "app/shared/types/event.types";
import {isNil} from "lodash";

@Injectable()
export class EventListingParamsService {

	private _paramsState?: EventSearchQuery;
	private _defaultState: EventSearchQuery = {
		page: 0,
		size: 20,
	}

	constructor() {
	}

	retrieveQueryParams(): EventSearchQuery {
		if (isNil(this._paramsState)) {
			this._paramsState = {...this._defaultState};
		}
		return this._paramsState;
	}

	resetParams() {
		this._paramsState = {...this._defaultState};
	}

	updateQueryParams(params: Partial<EventSearchQuery>) {
		if (isNil(this._paramsState)) {
			this._paramsState = {...this._defaultState, ...params};
		}
		this._paramsState = {...this._paramsState, ...params};
	}
}
