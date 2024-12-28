import {Component, Inject} from '@angular/core';
import {MatButton} from "@angular/material/button";
import {MAT_DIALOG_DATA, MatDialogActions, MatDialogContent, MatDialogRef} from "@angular/material/dialog";
import {NgOptimizedImage} from "@angular/common";
import {isNil} from "lodash";
import {InfoDialogInitialData} from "app/shared/dialog/info-dialog/types/info-dialog.types";

@Component({
	selector: 'app-info-dialog',
	standalone: true,
	imports: [
		MatButton,
		MatDialogActions,
		MatDialogContent,
		NgOptimizedImage
	],
	templateUrl: './info-dialog.component.html',
	styleUrl: './info-dialog.component.scss'
})
export class InfoDialogComponent {
	title!: string;
	message!: string;
	label = 'Ok';

	constructor(private dialogRef: MatDialogRef<InfoDialogComponent>,
				@Inject(MAT_DIALOG_DATA) private dialogData: InfoDialogInitialData) {
		this.title = dialogData.title;
		this.message = dialogData.message;
		if (!isNil(dialogData.buttonLabel)) {
			this.label = dialogData.buttonLabel;
		}
	}

	close(){
		this.dialogRef.close();
	}
}
