import {Component, Inject} from '@angular/core';
import {MatButton} from "@angular/material/button";
import {
	MAT_DIALOG_DATA,
	MatDialogActions,
	MatDialogContent,
	MatDialogRef
} from "@angular/material/dialog";
import {NgIf, NgOptimizedImage} from "@angular/common";
import {
	ConfirmationDialogAction,
	ConfirmationDialogData
} from "app/shared/dialog/confirmation-dialog/types/confirmation-dialog.types";
import {isNil} from "lodash";

@Component({
	selector: 'app-confirmation-dialog',
	standalone: true,
	imports: [
		MatButton,
		MatDialogActions,
		MatDialogContent,
		NgOptimizedImage
	],
	templateUrl: './confirmation-dialog.component.html',
	styleUrl: './confirmation-dialog.component.scss'
})
export class ConfirmationDialogComponent {

	title!: string;
	message!: string;
	acceptLabel= 'Tak';
	declineLabel = 'Nie';

	constructor(private dialogRef: MatDialogRef<ConfirmationDialogComponent>,
				@Inject(MAT_DIALOG_DATA) private dialogData: ConfirmationDialogData) {
		this.title = dialogData.title;
		this.message = dialogData.message;
		if (!isNil(dialogData.acceptLabel)) {
			this.acceptLabel = dialogData.acceptLabel;
		}
		if (!isNil(dialogData.declineLabel)) {
			this.declineLabel = dialogData.declineLabel;
		}
	}

	decline() {
		this.dialogRef.close(ConfirmationDialogAction.DECLINE);
	}

	accept() {
		this.dialogRef.close(ConfirmationDialogAction.ACCEPT);
	}
}
