import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {BusinessBranch} from "app/shared/types/business-branch.types";
import {FormBuilder, FormGroup} from "@angular/forms";
import {
	PatentListingParamsService
} from "app/features/patent/features/patent-listing/service/patent-listing-params.service";
import {PatentSearchQuery} from "app/shared/types/patent.types";
import moment from "moment";

@Component({
  selector: 'app-filter-panel',
  templateUrl: './filter-panel.component.html',
  styleUrl: './filter-panel.component.scss'
})
export class FilterPanelComponent implements OnInit {

	@Input() businessBranches!: BusinessBranch[];

	filterForm!: FormGroup;

	@Output() panelClosed = new EventEmitter<void>();
	@Output() searchClicked = new EventEmitter<void>();

	constructor(private formBuilder: FormBuilder, private paramsService: PatentListingParamsService) {
	}

	ngOnInit() {
		this.initForm();
		this.setInitialValues();
	}

	handleSearch() {
		this.buildSearchParams();
		this.searchClicked.emit();
	}

	private initForm() {
		const businessBranchesGroups = this.initBusinessBranchesControls();
		this.filterForm = this.formBuilder.group({
			title: [null],
			dateFrom: [null],
			...businessBranchesGroups.controls
		});
	}

	private setInitialValues() {
		this.paramsService.retrieveQueryParams().subscribe(params => {
			const {title, dateCreated, businessBranchesIds} = params;
			this.filterForm.patchValue({title: title || null, dateFrom: dateCreated || null}, {emitEvent: false});
			(businessBranchesIds || []).forEach(id => {
				if (this.filterForm.contains(String(id))) {
					this.filterForm.get(String(id))?.patchValue(true);
				}
			});
			this.filterForm.updateValueAndValidity();
			this.searchClicked.emit();
		});
	}

	private initBusinessBranchesControls(): FormGroup {
		return this.formBuilder.group(
			this.businessBranches.reduce((controls: any, branch: BusinessBranch) => {
				return {...controls, [branch.id]: [false]}
			}, {})
		);
	}

	private buildSearchParams(): Partial<PatentSearchQuery> {
		const selectedBranches: number[] = Object.keys(this.filterForm.value)
			.filter(valueKey => this.isBusinessBranchValue(valueKey) && this.filterForm.value[valueKey] === true)
			.map(valueKey => +valueKey);
		const formValue = this.filterForm.value;

		const params: Partial<PatentSearchQuery> = {
			businessBranchesIds: selectedBranches
		}
		if (formValue['dateFrom']) {
			const dateFrom = moment(formValue['dateFrom']);
			this.clearMoment(dateFrom);
			params.dateCreated = dateFrom.format('YYYY-MM-DD');
		}
		if (formValue['title']) {
			params.title = formValue['title'];
		}
		this.paramsService.updateSearchParams(params);
		return params;
	}

	private isBusinessBranchValue(key: string): boolean {
		return key !== 'title' && key !== 'dateFrom';
	}

	private clearMoment(moment: moment.Moment) {
		moment.set({hours: 0, minutes: 0, seconds: 0});
	}
}
