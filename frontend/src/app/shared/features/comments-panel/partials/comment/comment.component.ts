import {Component, EventEmitter, Input, Output} from '@angular/core';
import {MatButton} from "@angular/material/button";
import {MatIcon} from "@angular/material/icon";
import {MatPrefix} from "@angular/material/form-field";
import {halfRotateRightAnimation} from "app/shared/utils/animations/rotate.animations";
import {AsyncPipe, DatePipe, NgIf, TitleCasePipe} from "@angular/common";
import {fastAppearAnimation} from "app/shared/utils/animations/appear.animation";
import {MatTooltip} from "@angular/material/tooltip";
import {map, Observable, of} from "rxjs";
import {MatDialog, MatDialogRef} from "@angular/material/dialog";
import {FormUsageMode} from "app/shared/types/util.types";
import {CommentFormComponent} from "app/shared/features/comments-panel/partials/comment-form/comment-form.component";
import {isNil} from "lodash";
import {ConfirmationDialogService} from "app/shared/dialog/confirmation-dialog/service/confirmation-dialog.service";
import {ConfirmationDialogAction} from "app/shared/dialog/confirmation-dialog/types/confirmation-dialog.types";
import {Comment} from "app/shared/types/comment.types";
import moment from "moment";
import {SecurityService} from "app/shared/service/security.service";
import {UserService} from "app/shared/service/user.service";

@Component({
	selector: 'app-comment',
	standalone: true,
	imports: [
		MatButton,
		MatIcon,
		MatPrefix,
		NgIf,
		MatTooltip,
		AsyncPipe,
		TitleCasePipe,
		DatePipe
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

	constructor(private dialog: MatDialog,
				private userService: UserService,
				private confirmationService: ConfirmationDialogService) {
	}

	showRepliesSection(): boolean {
		if (this.isReply) {
			return false;
		}
		return this.comment.replies.length > 0;
	}

	isCommentAuthor$(): Observable<boolean> {
		return this.userService.getLoggedUserDetails()
			.pipe(map(details => {
				if (isNil(details)) {
					return false;
				}
				return this.comment.authorEmail === details.email;
			}));
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
		const dialogRef = this.openFormDialog(FormUsageMode.EDIT, this.comment.content);
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

	getAuthorName(): string {
		return this.comment.authorName.concat(' ', this.comment.authorLastName);
	}

	isCommentFromToday(): boolean {
		const commentDate = moment(this.comment.createdAt);
		commentDate.set({hours: 0, minutes: 0, seconds: 0, milliseconds: 0});
		const today = moment();
		today.set({hours: 0, minutes: 0, seconds: 0, milliseconds: 0});
		return today.isSame(commentDate);
	}

	private openFormDialog(mode: FormUsageMode, content?: string): MatDialogRef<CommentFormComponent> {
		return this.dialog.open(CommentFormComponent, {
			data: {mode, content}, minHeight: '10vh',
			minWidth: '70vh'
		})
	}

	private confirmDelete(): Observable<boolean> {
		return this.confirmationService.openDialog({
			title: 'Czy na pewno?',
			message: 'Usunięcie komentarza jest nieodwracalne'
		})
			.afterClosed()
			.pipe(
				map(confirmResult => confirmResult === ConfirmationDialogAction.ACCEPT)
			);
	}

	get repliesButtonLabel(): string {
		return this.repliesShown ? 'Ukryj odpowiedzi' : 'Pokaż odpowiedzi';
	}

	get isUserLoggedIn$(): Observable<boolean> {
		return this.userService.getLoggedUserDetails()
			.pipe(map(details => !isNil(details)));
	}
}
