import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {BusinessBranch} from "app/shared/types/business-branch.types";
import {slideInOutAnimation} from "app/shared/utils/animations/loading.animation";
import {PatentListingService} from "app/features/patent/features/patent-listing/service/patent-listing.service";
import {
	PatentListingParamsService
} from "app/features/patent/features/patent-listing/service/patent-listing-params.service";
import {PatentSearchQuery, PatentSearchResult} from "app/shared/types/patent.types";
import {PageEvent} from "@angular/material/paginator";

@Component({
	selector: 'app-patent-listing',
	templateUrl: './patent-listing.component.html',
	styleUrl: './patent-listing.component.scss',
	animations: [slideInOutAnimation]
})
export class PatentListingComponent implements OnInit {

	showSidePanel: boolean = false;
	sidePanelHidden: boolean = true;
	showPanelButton: boolean = true;
	isLoadingData: boolean = true;
	currentPage = 0;
	totalElements = 0;
	currentPageSize = 20;
	readonly availablePageSizes = [20, 50, 100];

	private _businessBranches?: BusinessBranch[];
	private _patents: PatentSearchResult[] = [];


	constructor(private activatedRoue: ActivatedRoute,
				private listingService: PatentListingService,
				private paramsService: PatentListingParamsService) {
	}

	ngOnInit() {
		this.initBusinessBranches();
	}

	handlePanelAnimationStart() {
		if (this.showSidePanel) {
			this.showPanelButton = false;
			this.sidePanelHidden = false;
		}
	}

	handleFilterChange() {
		this.isLoadingData = true;
		this.paramsService.retrieveQueryParams().subscribe(params => {
			const finalParams: PatentSearchQuery = {
				...params,
				size: this.currentPageSize,
				number: this.currentPage
			}
			this.downloadPatents(finalParams);
		});
	}

	handlePanelAnimationDone() {
		if (!this.showSidePanel) {
			this.sidePanelHidden = true;
			this.showPanelButton = true;
		}
	}

	handlePageEvent(event: PageEvent) {
		this.currentPageSize = event.pageSize;
		this.currentPage = event.pageIndex;
		this.handleFilterChange();
	}

	private downloadPatents(params: PatentSearchQuery) {
		this.listingService.getQueriedPatents(params).subscribe(result => {
			this._patents = result.content;
			this.currentPage = result.number;
			this.totalElements = result.totalElements || 0;
			this.isLoadingData = false;
		});
	}

	private initBusinessBranches(): void {
		this._businessBranches = this.activatedRoue.snapshot.data['businessBranches'];
	}

	get businessBranches(): BusinessBranch[] {
		return this._businessBranches || [];
	}

	get patents(): PatentSearchResult[] {
		return this._patents;
	}
}
