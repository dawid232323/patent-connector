import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {BusinessBranch} from "app/shared/types/business-branch.types";
import {UserService} from "app/shared/service/user.service";
import {
	loadingFromSideAnimation,
	loadingFromSideAnimationReversed
} from "app/shared/utils/animations/loading.animation";
import {map, switchMap} from "rxjs";
import {User} from "app/shared/types/user.types";
import {LoginComponent} from "app/features/common/features/login/login.component";
import {isNil} from "lodash";

@Component({
	selector: 'app-select-business-branches',
	templateUrl: './select-business-branches.component.html',
	styleUrl: './select-business-branches.component.scss',
	animations: [loadingFromSideAnimation, loadingFromSideAnimationReversed]
})
export class SelectBusinessBranchesComponent implements OnInit, OnDestroy {

	private _businessBranches: BusinessBranch[] = [];
	private _selectedBranchesIds = new Set<number>();
	private _isLoadingData = false;

	constructor(private activatedRoute: ActivatedRoute, private router: Router, private userService: UserService) {
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
		this._isLoadingData = true;
		const selectedBranches: number[] = Array.from(this._selectedBranchesIds)
		this.userService.getLoggedUserDetails()
			.pipe(
				map((user) => user!.id),
				switchMap((userId: number) => this.userService.updateSelectedBusinessBranches(userId, selectedBranches))
			).subscribe({
			next: (response) => this.handleUpdateSuccess(response),
			error: err => this.handleUpdateError(err)
		})

	}

	private handleUpdateSuccess(user: User) {
		this._isLoadingData = false;
		if (this.shouldRedirectToCustom()) {
			return this.router.navigate([this.activatedRoute.snapshot.queryParams[LoginComponent.NEXT_QUERY_PARAM]]);
		}
		return this.router.navigate(['/']);
	}

	private handleUpdateError(error: any) {
		this._isLoadingData = false;
		throw error;
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

	private shouldRedirectToCustom(): boolean {
		return !isNil(this.activatedRoute.snapshot.queryParams[LoginComponent.NEXT_QUERY_PARAM]);
	}

	get businessBranches(): BusinessBranch[] {
		return this._businessBranches;
	}

	get isLoadingData(): boolean {
		return this._isLoadingData;
	}
}
