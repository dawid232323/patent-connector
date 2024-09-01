import {Injectable} from '@angular/core';
import {ApiService} from "app/shared/service/api.service";
import {ResearchInstitutionService} from "app/shared/service/research-institution.service";
import {map, Observable} from "rxjs";
import {
	ResearchInstitutionRegisterPreview, ResearchInstitutionWorkerCreateDto
} from "app/features/common/features/register-research-institution-worker/types/research-institution-registration.types";
import {ResearchInstitution} from "app/shared/types/research-institution.types";
import {User} from "app/shared/types/user.types";

@Injectable({
	providedIn: 'root'
})
export class InstitutionWorkerRegistrationService {

	constructor(private apiService: ApiService, private institutionService: ResearchInstitutionService) {
	}

	findByWorkerEmail(email: string): Observable<ResearchInstitutionRegisterPreview[]> {
		return this.institutionService.findByWorkerEmailDomain(email)
			.pipe(map((institutions) => institutions.map(institution => {
				return {
					id: institution.id,
					name: institution.name,
					address: this.getAddressString(institution)
				}
			})))

	}

	registerResearchInstitutionWorker(dto: ResearchInstitutionWorkerCreateDto): Observable<User> {
		return <Observable<User>>this.apiService.post<ResearchInstitutionWorkerCreateDto, User>(dto, 'users/register-research-institution-worker');
	}

	private getAddressString(institution: ResearchInstitution): string {
		let baseAddressString = '';
		if (institution.city) {
			baseAddressString = baseAddressString.concat(institution.city);
		}
		if (institution.postalCode) {
			baseAddressString = baseAddressString.concat(',', ' ', institution.postalCode);
		}
		if (institution.country) {
			baseAddressString = baseAddressString.concat(',', ' ', institution.country);
		}
		return baseAddressString;
	}
}
