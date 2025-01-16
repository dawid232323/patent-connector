import {Component, EventEmitter, Input, Output} from '@angular/core';
import {MatButton, MatMiniFabButton} from "@angular/material/button";
import {MatIcon} from "@angular/material/icon";
import {MatPrefix, MatSuffix} from "@angular/material/form-field";
import {halfRotateRightAnimation} from "app/shared/utils/animations/rotate.animations";
import {AsyncPipe, NgIf} from "@angular/common";
import {fastAppearAnimation} from "app/shared/utils/animations/appear.animation";
import {MatTooltip} from "@angular/material/tooltip";
import {Observable, of} from "rxjs";

@Component({
	selector: 'app-comment',
	standalone: true,
	imports: [
		MatButton,
		MatIcon,
		MatSuffix,
		MatPrefix,
		NgIf,
		MatMiniFabButton,
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

	}

	handleEdit() {

	}

	handleDelete() {

	}

	get repliesButtonLabel(): string {
		return this.repliesShown ? 'Ukryj odpowiedzi' : 'Pokaż odpowiedzi';
	}
}
