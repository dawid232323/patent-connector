import {Injectable} from '@angular/core';
import {ApiService} from "app/shared/service/api.service";
import {InventionDemand} from "app/shared/types/invention-demand.types";
import {AppEndpoints} from "app/shared/types/api.types";
import {Observable} from "rxjs";

@Injectable({
	providedIn: 'root'
})
export class InventionDemandService {

	constructor(private apiService: ApiService) {
	}

	createDemand(data: InventionDemand): Observable<void> {
		return this.apiService.post(data, AppEndpoints.InventionDemands.inventionDemandsBase);
	}
}
