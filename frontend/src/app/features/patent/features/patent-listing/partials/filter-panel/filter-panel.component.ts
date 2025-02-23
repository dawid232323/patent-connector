import {ChangeDetectorRef, Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {BusinessBranch} from "app/shared/types/business-branch.types";
import {FormBuilder, FormGroup} from "@angular/forms";
import {
	PatentListingParamsService
} from "app/features/patent/features/patent-listing/service/patent-listing-params.service";
import {PatentSearchQuery} from "app/shared/types/patent.types";
import moment from "moment";
import {TreeNode} from "primeng/api";
import {BusinessBranchService} from "app/shared/service/business-branch.service";
import {TreeNodeExpandEvent} from "primeng/tree";
import {isNil} from "lodash";

@Component({
  selector: 'app-filter-panel',
  templateUrl: './filter-panel.component.html',
  styleUrl: './filter-panel.component.scss'
})
export class FilterPanelComponent implements OnInit {

	@Input() businessBranches!: BusinessBranch[];

	filterForm!: FormGroup;
	treeValues: TreeNode<BusinessBranch>[] = [];

	@Output() panelClosed = new EventEmitter<void>();
	@Output() searchClicked = new EventEmitter<void>();

	constructor(private formBuilder: FormBuilder,
				private paramsService: PatentListingParamsService,
				private changeDetectorRef: ChangeDetectorRef,
				private businessBranchService: BusinessBranchService,) {
	}

	ngOnInit() {
		this.mapBranchesToTreeNodes();
		this.initForm();
		this.setInitialValues();
	}

	handleSearch() {
		this.buildSearchParams();
		this.searchClicked.emit();
	}

	handleNodeExpand(event: TreeNodeExpandEvent) {
		event.node.loading = true;
		this.businessBranchService.getBusinessBranchChildren(event.node.data.id).subscribe(children => {
			const _node = {...event.node};
			_node.children = this.mapChildrenBranches(children);
			if (_node.parent) {
				const originalIndex = _node.parent.children!.findIndex(value => value.key === event.node.key);
				_node.parent.children![originalIndex] = {..._node, loading: false};
			} else {
				const originalIndex = this.treeValues.findIndex(value => value.key === event.node.key);
				this.treeValues[originalIndex] = {..._node, loading: false};
			}
			this.changeDetectorRef.detectChanges();
		});
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

	private mapBranchesToTreeNodes() {
		this.treeValues = this.businessBranches.map(branch => ({
			key: String(branch.id),
			label: branch.displayName,
			data: branch,
			leaf: false,
			selectable: true,
			children: []
		}));
	}

	private mapChildrenBranches(branches: BusinessBranch[]): TreeNode<BusinessBranch>[] {
		return branches.map(branch => ({
			key: String(branch.id),
			label: branch.displayName,
			data: branch,
			leaf: !isNil(branch.code),
			selectable: true,
			children: []
		}));
	}

	private isBusinessBranchValue(key: string): boolean {
		return key !== 'title' && key !== 'dateFrom';
	}

	private clearMoment(moment: moment.Moment) {
		moment.set({hours: 0, minutes: 0, seconds: 0});
	}
}
