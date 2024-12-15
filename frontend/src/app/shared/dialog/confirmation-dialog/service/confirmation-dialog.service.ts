import {Injectable} from '@angular/core';
import {MatDialog, MatDialogRef} from "@angular/material/dialog";
import {ConfirmationDialogComponent} from "app/shared/dialog/confirmation-dialog/confirmation-dialog.component";
import {ConfirmationDialogData} from "app/shared/dialog/confirmation-dialog/types/confirmation-dialog.types";

@Injectable({
	providedIn: 'root'
})
export class ConfirmationDialogService {

	constructor(private dialog: MatDialog) {
	}

	openDialog(data: ConfirmationDialogData): MatDialogRef<ConfirmationDialogComponent> {
		return this.dialog.open(ConfirmationDialogComponent, {
			data,
			minHeight: '10vh',
			minWidth: '70vh'
		});
	}
}
