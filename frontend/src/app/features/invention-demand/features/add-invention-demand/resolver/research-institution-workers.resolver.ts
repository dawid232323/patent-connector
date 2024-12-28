import {ResolveFn} from '@angular/router';
import {User} from "app/shared/types/user.types";
import {inject} from "@angular/core";
import {UserService} from "app/shared/service/user.service";

export const researchInstitutionWorkersResolver: ResolveFn<User[]> = (route, state) => {
	const userService = inject(UserService);
	return userService.getResearchInstitutionWorkers();
};
