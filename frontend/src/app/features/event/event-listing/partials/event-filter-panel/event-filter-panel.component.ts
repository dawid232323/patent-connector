import {Component, EventEmitter, Input, OnDestroy, OnInit, Output} from '@angular/core';
import {BusinessBranch} from "app/shared/types/business-branch.types";
import {FormBuilder, FormControl, FormGroup} from "@angular/forms";
import {futureDateValidator} from "app/shared/utils/validation/validators.fn";
import {EventSearchQuery} from "app/shared/types/event.types";
import moment from "moment";
import {EventListingParamsService} from "app/features/event/event-listing/service/event-listing-params.service";
import {isNil} from "lodash";

@Component({
	selector: 'app-event-filter-panel',
	templateUrl: './event-filter-panel.component.html',
	styleUrl: './event-filter-panel.component.scss'
})
export class EventFilterPanelComponent implements OnInit, OnDestroy {
	@Input() businessBranches!: BusinessBranch[];

	filterForm!: FormGroup;

	@Output() panelClosed = new EventEmitter<void>();
	@Output() searchClicked = new EventEmitter<Partial<EventSearchQuery>>();

	private _branchesCodes: Set<string> = new Set<string>();

	constructor(private formBuilder: FormBuilder, private paramsService: EventListingParamsService) {
	}

	ngOnInit() {
		this.initFilterForm();
		this.setInitialData();
	}

	ngOnDestroy() {
	}

	handleSearchClicked() {
		if (this.filterForm.invalid) {
			return;
		}
		const queryParams = this.resolveQueryParams();
		this.panelClosed.emit();
		this.searchClicked.emit(queryParams);
	}

	handleFilterClear() {
		this.filterForm.reset();
		this.paramsService.resetParams();
		this.panelClosed.emit();
		this.searchClicked.emit();
	}

	private setInitialData() {
		const params = this.paramsService.retrieveQueryParams();
		if (!isNil(params.title)) {
			this.filterForm.patchValue({title: params.title}, {emitEvent: false, onlySelf: true});
		}
		if (!isNil(params.dateFrom)) {
			this.filterForm.patchValue({dateFrom: new Date(params.dateFrom)}, {emitEvent: false, onlySelf: true});
		}
		if (!isNil(params.dateTo)) {
			this.filterForm.patchValue({dateTo: new Date(params.dateTo)}, {emitEvent: false, onlySelf: true});
		}
		params.sectionBranchesCodes?.forEach((code) => {
			this.filterForm.patchValue({[code.trim()]: true}, {emitEvent: false, onlySelf: true});
		});
		this.filterForm.updateValueAndValidity();
	}

	private initFilterForm() {
		this.filterForm = this.formBuilder.group({
			title: [null],
			dateFrom: [null, [futureDateValidator()]],
			dateTo: [null, [futureDateValidator()]],
			...this.initBusinessBranchesControls()
		});
	}

	private initBusinessBranchesControls(): { [key: string]: FormControl } {
		return this.businessBranches.reduce((controls: any, businessBranch) => {
			this._branchesCodes.add(businessBranch.section.trim());
			return {...controls, [businessBranch.section.trim()]: new FormControl(false)};
		}, {} as { [key: string]: FormControl });
	}

	private resolveQueryParams(): Partial<EventSearchQuery> {
		const searchQuery: Partial<EventSearchQuery> = {};
		const formValue = this.filterForm.value;
		const selectedBranchesCodes = Object.keys(formValue)
			.filter(controlKey => this._branchesCodes.has(controlKey) && formValue[controlKey] === true);
		if (formValue['title'] && formValue['title'].trim() !== '') {
			searchQuery.title = formValue['title'];
		}
		if (formValue['dateFrom']) {
			const dateFrom = moment(formValue['dateFrom']);
			this.clearMoment(dateFrom);
			searchQuery.dateFrom = dateFrom.format('YYYY-MM-DDTHH:mm');
		}
		if (formValue['dateTo']) {
			const dateTo = moment(formValue['dateTo']);
			this.clearMoment(dateTo);
			searchQuery.dateTo = dateTo.format('YYYY-MM-DDTHH:mm');
		}
		searchQuery.sectionBranchesCodes = selectedBranchesCodes;
		return searchQuery;
	}

	private clearMoment(moment: moment.Moment) {
		moment.set({hours: 0, minutes: 0, seconds: 0});
	}
}
