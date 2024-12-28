import {Injectable} from '@angular/core';
import {InfoDialogInitialData} from "app/shared/dialog/info-dialog/types/info-dialog.types";
import {MatDialog, MatDialogRef} from "@angular/material/dialog";
import {InfoDialogComponent} from "app/shared/dialog/info-dialog/info-dialog.component";

@Injectable({
	providedIn: 'root'
})
export class InfoDialogService {

	constructor(private matDialog: MatDialog) {
	}

	open(dialogTitle: string, dialogMessage: string, buttonLabel?: string): MatDialogRef<InfoDialogComponent> {
		const initialData: InfoDialogInitialData = {title: dialogTitle, message: dialogMessage, buttonLabel};
		return this.matDialog.open(InfoDialogComponent, {data: initialData});
	}
}
