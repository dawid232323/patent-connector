import {ResolveFn} from '@angular/router';
import {BusinessBranch} from "app/shared/types/business-branch.types";
import {inject} from "@angular/core";
import {BusinessBranchService} from "app/shared/service/business-branch.service";

export const selectBusinessBranchesResolver: ResolveFn<BusinessBranch[]> = (route, state) => {
	const businessBranchService = inject(BusinessBranchService);
	return businessBranchService.getAllSectionBusinessBranches();
};
