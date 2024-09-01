import {Injectable} from '@angular/core';
import {ApiService} from "app/shared/service/api.service";
import {CreateEntrepreneurDto} from "app/features/common/features/register/types/registration.types";
import {Observable} from "rxjs";
import {HttpEvent, HttpResponse} from "@angular/common/http";
import {User} from "app/shared/types/user.types";

@Injectable({
	providedIn: 'root'
})
export class RegisterService {

	constructor(private apiService: ApiService) {
	}

	registerUser(registerBody: CreateEntrepreneurDto): Observable<User> {
		return <Observable<User>>this.apiService.post<CreateEntrepreneurDto, User>(registerBody, 'users/register');
	}
}
