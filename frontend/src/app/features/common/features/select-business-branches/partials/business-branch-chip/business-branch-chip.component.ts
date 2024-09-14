import {Component, EventEmitter, Input, Output} from '@angular/core';
import {BusinessBranch} from "app/shared/types/business-branch.types";

@Component({
  selector: 'app-business-branch-chip',
  templateUrl: './business-branch-chip.component.html',
  styleUrl: './business-branch-chip.component.scss'
})
export class BusinessBranchChipComponent {
	@Input() businessBranch!: BusinessBranch;
	@Input() isBranchSelected: boolean = false;

	@Output() selectionChange = new EventEmitter<void>();
}
