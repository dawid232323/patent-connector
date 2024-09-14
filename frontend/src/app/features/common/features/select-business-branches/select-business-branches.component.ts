import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {BusinessBranch} from "app/shared/types/business-branch.types";
import {UserService} from "app/shared/service/user.service";
import {
	loadingFromSideAnimation,
	loadingFromSideAnimationReversed
} from "app/shared/utils/animations/loading.animation";

@Component({
	selector: 'app-select-business-branches',
	templateUrl: './select-business-branches.component.html',
	styleUrl: './select-business-branches.component.scss',
	animations: [loadingFromSideAnimation, loadingFromSideAnimationReversed]
})
export class SelectBusinessBranchesComponent implements OnInit, OnDestroy {

	private _businessBranches: BusinessBranch[] = [];
	private _selectedBranchesIds = new Set();

	constructor(private activatedRoute: ActivatedRoute, private userService: UserService) {
	}

	ngOnInit() {
		this.assignBusinessBranches();
		this.initSelectedBranches();
	}

	ngOnDestroy() {
	}

	isBranchSelected(branchId: number): boolean {
		return this._selectedBranchesIds.has(branchId);
	}

	onSelectionChanged(branchId: number) {
		if (this.isBranchSelected(branchId)) {
			this._selectedBranchesIds.delete(branchId);
		} else {
			this._selectedBranchesIds.add(branchId);
		}
	}

	canSubmit(): boolean {
		return this._selectedBranchesIds.size > 0;
	}

	submit() {
		if (!this.canSubmit()) {
			return;
		}
	}

	private assignBusinessBranches() {
		this._businessBranches = this.activatedRoute.snapshot.data['businessBranches'] || [];
	}

	private initSelectedBranches() {
		this.userService.getLoggedUserDetails().subscribe((user) => {
			user?.entrepreneursData?.interestBranches?.forEach(branch => {
				this._selectedBranchesIds.add(branch.id);
			});
		});
	}

	get businessBranches(): BusinessBranch[] {
		return this._businessBranches;
	}
}
