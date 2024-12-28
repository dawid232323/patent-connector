import {ResolveFn} from '@angular/router';
import {User} from "app/shared/types/user.types";
import {UserService} from "app/shared/service/user.service";
import {inject} from "@angular/core";
import {Observable} from "rxjs";

export const loggedUserResolver: ResolveFn<User> = (route, state) => {
	const userService: UserService = inject(UserService);
	return <Observable<User>>userService.getLoggedUserDetails();
};
