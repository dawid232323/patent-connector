import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogActions, MatDialogContent, MatDialogRef} from "@angular/material/dialog";
import {NgIf, NgOptimizedImage} from "@angular/common";
import {CommentFormInitialData} from "app/shared/types/comment.types";
import {FormUsageMode} from "app/shared/types/util.types";
import {FormBuilder, FormControl, FormGroup, ReactiveFormsModule} from "@angular/forms";
import {ValidationService} from "app/shared/utils/validation/validation.service";
import {MatFormField, MatLabel} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {CdkTextareaAutosize} from "@angular/cdk/text-field";
import {MatButton} from "@angular/material/button";

@Component({
	selector: 'app-comment-form',
	standalone: true,
	imports: [
		MatDialogContent,
		NgOptimizedImage,
		NgIf,
		MatFormField,
		MatInput,
		ReactiveFormsModule,
		CdkTextareaAutosize,
		MatLabel,
		MatDialogActions,
		MatButton
	],
	templateUrl: './comment-form.component.html',
	styleUrl: './comment-form.component.scss'
})
export class CommentFormComponent implements OnInit {

	commentForm!: FormGroup;

	constructor(@Inject(MAT_DIALOG_DATA) private data: CommentFormInitialData,
				private formBuilder: FormBuilder,
				private validationService: ValidationService,
				private dialogRef: MatDialogRef<CommentFormComponent>) {
	}

	ngOnInit() {
		this.initForm();
		this.setInitialValues();
	}

	submitForm() {
		if (this.commentForm.invalid) {
			return;
		}
		this.dialogRef.close(this.contentCtrl.value);
	}

	private initForm() {
		this.commentForm = this.formBuilder.group({
			content: [null, this.validationService.getTextualValidators({required: true, minLength: 1, maxLength: 700})],
		});
	}

	private setInitialValues() {
		if (this.data.mode === FormUsageMode.EDIT) {
			this.contentCtrl.setValue(this.data.content);
		}
	}

	get title(): string {
		return this.data.mode === FormUsageMode.CREATE ? 'Dodaj Komentarz' : 'Edytuj Komentarz';
	}

	get contentCtrl(): FormControl {
		return this.commentForm.get('content') as FormControl;
	}
}
