import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {BusinessBranch} from "app/shared/types/business-branch.types";
import {FormBuilder, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-filter-panel',
  templateUrl: './filter-panel.component.html',
  styleUrl: './filter-panel.component.scss'
})
export class FilterPanelComponent implements OnInit {

	@Input() businessBranches!: BusinessBranch[];

	filterForm!: FormGroup;

	@Output() panelClosed = new EventEmitter<void>();

	constructor(private formBuilder: FormBuilder) {
	}

	ngOnInit() {
		this.initForm();
	}

	private initForm() {
		const businessBranchesGroups = this.initBusinessBranchesControls();
		this.filterForm = this.formBuilder.group({
			title: [null],
			dateFrom: [null],
			...businessBranchesGroups.controls
		});
		console.log(this.filterForm)
	}

	private initBusinessBranchesControls(): FormGroup {
		return this.formBuilder.group(
			this.businessBranches.reduce((controls: any, branch: BusinessBranch) => {
				return {...controls, [branch.id]: [false]}
			}, {})
		);
	}
}
