import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from '@angular/router';
import {Observable} from 'rxjs';
import {Patent} from "app/shared/types/patent.types";
import {PatentService} from "app/features/patent/service/patent.service";

@Injectable({
	providedIn: 'root'
})
export class PatentDetailsResolver implements Resolve<Patent> {

	constructor(private patentService: PatentService) {
	}

	resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Patent> {
		const patentId = route.paramMap.get('id')!;
		return this.patentService.getPatent(patentId);
	}
}
