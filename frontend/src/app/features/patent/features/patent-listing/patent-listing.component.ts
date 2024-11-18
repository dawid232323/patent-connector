import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {BusinessBranch} from "app/shared/types/business-branch.types";
import {slideInOutAnimation} from "app/shared/utils/animations/loading.animation";

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

	private _businessBranches?: BusinessBranch[];

	constructor(private activatedRoue: ActivatedRoute) {
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

	handlePanelAnimationDone() {
		if (!this.showSidePanel) {
			this.sidePanelHidden = true;
			this.showPanelButton = true;
		}
	}

	private initBusinessBranches(): void {
		this._businessBranches = this.activatedRoue.snapshot.data['businessBranches'];
	}

	get businessBranches(): BusinessBranch[] {
		return this._businessBranches || [];
	}
}
