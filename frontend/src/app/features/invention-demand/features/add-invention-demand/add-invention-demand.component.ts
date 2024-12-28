import {Component, OnInit} from '@angular/core';
import {InfoDialogService} from "app/shared/dialog/info-dialog/service/info-dialog.service";
import {
	infoDialogMessage,
	infoDialogTitle, successDialogTitle
} from "app/features/invention-demand/features/add-invention-demand/types/invention-demand.types";
import {InventionDemand} from "app/shared/types/invention-demand.types";
import {InventionDemandService} from "app/features/invention-demand/service/invention-demand.service";
import {ErrorDialogService} from "app/shared/dialog/error-dialog/service/error-dialog.service";
import {ErrorResponse} from "app/shared/types/error.types";
import {Router} from "@angular/router";

@Component({
  selector: 'app-add-invention-demand',
  templateUrl: './add-invention-demand.component.html',
  styleUrl: './add-invention-demand.component.scss'
})
export class AddInventionDemandComponent implements OnInit {

	isLoadingData: boolean = false;

	constructor(private dialogService: InfoDialogService,
				private errorDialogService: ErrorDialogService,
				private router: Router,
				private inventionDemandService: InventionDemandService) {
	}

	ngOnInit() {
		this.openInfoDialog();
	}

	handleSubmit(formData: InventionDemand) {
		this.isLoadingData = true;
		this.inventionDemandService.createDemand(formData)
			.subscribe({
				next: () => this.handleSuccess(),
				error: error => this.handleError(error)
			});
	}

	private openInfoDialog() {
		this.dialogService.open(infoDialogTitle, infoDialogMessage);
	}

	private handleSuccess() {
		this.isLoadingData = false;
		this.dialogService.open(successDialogTitle, successDialogTitle).afterClosed().subscribe(() => {
			this.router.navigate(['patents', 'listing']);
		});
	}

	private handleError(error: ErrorResponse) {
		this.isLoadingData = false;
		this.errorDialogService.openHttpResponseError(error);
	}
}
