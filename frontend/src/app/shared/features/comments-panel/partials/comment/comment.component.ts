import {Component, EventEmitter, Input, Output} from '@angular/core';
import {MatButton} from "@angular/material/button";
import {MatIcon} from "@angular/material/icon";
import {MatPrefix} from "@angular/material/form-field";
import {halfRotateRightAnimation} from "app/shared/utils/animations/rotate.animations";
import {AsyncPipe, NgIf} from "@angular/common";
import {fastAppearAnimation} from "app/shared/utils/animations/appear.animation";
import {MatTooltip} from "@angular/material/tooltip";
import {map, Observable, of} from "rxjs";
import {MatDialog, MatDialogRef} from "@angular/material/dialog";
import {FormUsageMode} from "app/shared/types/util.types";
import {CommentFormComponent} from "app/shared/features/comments-panel/partials/comment-form/comment-form.component";
import {isNil} from "lodash";
import {ConfirmationDialogService} from "app/shared/dialog/confirmation-dialog/service/confirmation-dialog.service";
import {ConfirmationDialogAction} from "app/shared/dialog/confirmation-dialog/types/confirmation-dialog.types";

@Component({
	selector: 'app-comment',
	standalone: true,
	imports: [
		MatButton,
		MatIcon,
		MatPrefix,
		NgIf,
		MatTooltip,
		AsyncPipe
	],
	templateUrl: './comment.component.html',
	styleUrl: './comment.component.scss',
	animations: [halfRotateRightAnimation, fastAppearAnimation]
})
export class CommentComponent {

	@Input() isReply: boolean = false;
	@Input() comment!: Comment;

	@Output() commentReply = new EventEmitter<string>();
	@Output() commentEdit = new EventEmitter<string>();
	@Output() commentDelete = new EventEmitter<void>();

	repliesShown = false;

	constructor(private dialog: MatDialog, private confirmationService: ConfirmationDialogService) {
	}

	showRepliesSection(): boolean {
		if (this.isReply) {
			return false;
		}
		return true;
		// return this.comment.length > 0;
	}

	isCommentAuthor$(): Observable<boolean> {
		return of(true);
	}

	handleReply() {
		const dialogRef = this.openFormDialog(FormUsageMode.CREATE);
		dialogRef.afterClosed().subscribe(result => {
			if (isNil(result)) {
				return;
			}
			this.commentReply.emit(result);
		});
	}

	handleEdit() {
		const dialogRef = this.openFormDialog(FormUsageMode.EDIT);
		dialogRef.afterClosed().subscribe(result => {
			if (isNil(result)) {
				return;
			}
			this.commentEdit.emit(result);
		});
	}

	handleDelete() {
		this.confirmDelete().subscribe(isConfirmed => {
			if (isConfirmed) {
				this.commentDelete.emit();
			}
		});
	}

	private openFormDialog(mode: FormUsageMode, content?: string): MatDialogRef<CommentFormComponent> {
		return this.dialog.open(CommentFormComponent, {
			data: {mode, content}, minHeight: '10vh',
			minWidth: '70vh'
		})
	}

	private confirmDelete(): Observable<boolean> {
		return this.confirmationService.openDialog({title: 'Czy na pewno?', message: 'Usunięcie komentarza jest nieodwracalne'})
			.afterClosed()
			.pipe(
				map(confirmResult => confirmResult === ConfirmationDialogAction.ACCEPT)
			);
	}

	get repliesButtonLabel(): string {
		return this.repliesShown ? 'Ukryj odpowiedzi' : 'Pokaż odpowiedzi';
	}
}
