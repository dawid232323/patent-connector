import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {BusinessBranch} from "app/shared/types/business-branch.types";
import {Event, EventSearchQuery} from "app/shared/types/event.types";
import {slideInOutAnimation} from "app/shared/utils/animations/loading.animation";
import {PageEvent} from "@angular/material/paginator";
import {EventListingParamsService} from "app/features/event/event-listing/service/event-listing-params.service";
import {EventService} from "app/features/event/service/event.service";

@Component({
	selector: 'app-event-listing',
	templateUrl: './event-listing.component.html',
	styleUrl: './event-listing.component.scss',
	animations: [slideInOutAnimation]
})
export class EventListingComponent implements OnInit, OnDestroy {

	isLoadingData: boolean = false;
	showSidePanel: boolean = false;
	sidePanelHidden: boolean = true;
	showPanelButton: boolean = true;
	currentPage = 0;
	totalElements = 0;
	currentPageSize = 20;

	readonly availablePageSizes = [20, 50, 100];

	private _businessBranches!: BusinessBranch[];
	private _events: Event[] = [];

	constructor(private activatedRoute: ActivatedRoute,
				private eventService: EventService,
				private listingParamsService: EventListingParamsService) {
	}

	ngOnInit() {
		this.initData();
		this.downloadData();
	}

	ngOnDestroy() {
	}

	handlePanelAnimationStart() {
		if (this.showSidePanel) {
			this.showPanelButton = false;
			this.sidePanelHidden = false;
		}
	}

	handlePanelAnimationDone() {
		if (!this.showSidePanel) {
			this.sidePanelHidden = true;
			this.showPanelButton = true;
		}
	}

	handleSearchEvents(queryParams: Partial<EventSearchQuery>) {
		this.listingParamsService.updateQueryParams({...queryParams, page: 0});
		this.downloadData();
	}

	handlePageEvent(event: PageEvent) {
		this.currentPageSize = event.pageSize;
		this.currentPage = event.pageIndex;
		this.listingParamsService.updateQueryParams({page: this.currentPage, size: this.currentPageSize});
		this.downloadData();
	}

	private initData() {
		this._businessBranches = this.activatedRoute.snapshot.data['businessBranches'];
	}

	private downloadData() {
		this.isLoadingData = true;
		const params = this.listingParamsService.retrieveQueryParams();
		this.eventService.searchEvents(params).subscribe((events) => {
			this.currentPage = events.number;
			this.currentPageSize = events.size;
			this._events = events.content;
			this.totalElements = events.totalElements || 0;
			this.isLoadingData = false;
		});
	}

	get businessBranches(): BusinessBranch[] {
		return this._businessBranches;
	}

	get events(): Event[] {
		return this._events;
	}
}
