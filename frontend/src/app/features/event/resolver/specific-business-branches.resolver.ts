import {ResolveFn} from '@angular/router';
import {inject} from "@angular/core";
import {BusinessBranchService} from "app/shared/service/business-branch.service";
import {BusinessBranch} from "app/shared/types/business-branch.types";

export const specificBusinessBranchesResolver: ResolveFn<BusinessBranch[]> = (route, state) => {
	const businessBranchService = inject(BusinessBranchService);
	return businessBranchService.getAllSpecificBusinessBranches();
};
