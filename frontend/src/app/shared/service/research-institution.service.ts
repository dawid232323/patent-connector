import {Injectable} from '@angular/core';
import {ApiService} from "app/shared/service/api.service";
import {Observable} from "rxjs";
import {ResearchInstitution} from "app/shared/types/research-institution.types";
import {AppEndpoints} from "app/shared/types/api.types";

@Injectable({
	providedIn: 'root'
})
export class ResearchInstitutionService {

	constructor(private apiService: ApiService) {
	}

	findByWorkerEmailDomain(workerEmail: string): Observable<ResearchInstitution[]> {
		return this.apiService.get(`${AppEndpoints.ResearchInstitutionEndpoints.resInstitutionFind}/${workerEmail}`)
	}
}
