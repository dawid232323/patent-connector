import {Injectable} from '@angular/core';
import {ApiService} from "app/shared/service/api.service";
import {Observable} from "rxjs";
import {SetPasswordDto} from "app/features/common/features/activate-account/types/activate-account.model";

@Injectable({
	providedIn: 'root'
})
export class ActivateAccountService {

	constructor(private apiService: ApiService) {
	}

	activateAccount(password: string, token: string): Observable<any> {
		const dto = this.resolveDto(password, token);
		return this.apiService.post(dto, 'users/set-initial-password');
	}

	private resolveDto(password: string, token: string): SetPasswordDto {
		return {
			password: password, validationToken: token
		}
	}
}
