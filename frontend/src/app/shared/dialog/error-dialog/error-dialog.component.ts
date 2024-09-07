import {Component, Inject, OnDestroy, OnInit} from '@angular/core';
import {
	MAT_DIALOG_DATA,
	MatDialogActions, MatDialogClose,
	MatDialogContent,
	MatDialogRef,
	MatDialogTitle
} from "@angular/material/dialog";
import {ErrorDialogData} from "app/shared/dialog/error-dialog/types/error-dialog.types";
import {MatButton} from "@angular/material/button";
import {NgIf, NgOptimizedImage} from "@angular/common";

@Component({
	selector: 'app-error-dialog',
	standalone: true,
	imports: [
		MatDialogTitle,
		MatDialogContent,
		MatDialogActions,
		MatButton,
		MatDialogClose,
		NgOptimizedImage,
		NgIf
	],
	templateUrl: './error-dialog.component.html',
	styleUrl: './error-dialog.component.scss'
})
export class ErrorDialogComponent implements OnInit, OnDestroy {

	constructor(private dialogRef: MatDialogRef<ErrorDialogComponent>,
				@Inject(MAT_DIALOG_DATA) private dialogData: ErrorDialogData) {
	}

	ngOnInit() {
	}

	ngOnDestroy() {
	}

	get title(): string {
		return this.dialogData.title;
	}

	get message(): string {
		return this.dialogData.errorMessage;
	}

	get uuid(): string {
		return this.dialogData.uuid;
	}

	get additionalMessage(): string | undefined {
		return this.dialogData.additionalExceptionMessage;
	}

	get statusCode(): number | string | undefined {
		return this.dialogData.statusCode;
	}

	get buttonMessage(): string | undefined {
		return this.dialogData.acceptButtonMessage || 'Ok';
	}

	get errorCause(): string | undefined {
		return this.dialogData.errorCause;
	}
}
