import {Injectable} from '@angular/core';
import {map, Observable, of} from "rxjs";
import {Patent} from "app/shared/types/patent.types";
import {isNil} from "lodash";
import {ApiService} from "app/shared/service/api.service";
import {AppEndpoints} from "app/shared/types/api.types";

@Injectable()
export class PatentService {

	private _storedPatent?: Patent;
	private _lastPatentId?: number;

	constructor(private apiService: ApiService) {
	}

	getPatent(patentId: string | number): Observable<Patent> {
		if (!isNil(this._storedPatent) && this._lastPatentId === Number(patentId)) {
			return of(this._storedPatent);
		}
		return this.apiService.get<Patent>(AppEndpoints.PatentEndpoints.patentsBase.concat(`/${patentId}`))
			.pipe(map(patent => {
				this._storedPatent = patent;
				this._lastPatentId = Number(patentId);
				return patent;
			}));
	}
}
