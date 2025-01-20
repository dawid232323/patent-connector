import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {CommentType, Comment} from "app/shared/types/comment.types";
import {CommentComponent} from "app/shared/features/comments-panel/partials/comment/comment.component";
import {MatCard, MatCardActions, MatCardContent, MatCardHeader, MatCardTitle} from "@angular/material/card";
import {MatPaginator, PageEvent} from "@angular/material/paginator";
import {MatButton, MatIconButton} from "@angular/material/button";
import {MatIcon} from "@angular/material/icon";
import {MatDialog} from "@angular/material/dialog";
import {FormUsageMode} from "app/shared/types/util.types";
import {filter, Observable, switchMap, switchMapTo, take, tap} from "rxjs";
import {AsyncPipe, NgIf} from "@angular/common";
import {MatProgressSpinner} from "@angular/material/progress-spinner";
import {fastAppearAnimation} from "app/shared/utils/animations/appear.animation";
import {CommentsService} from "app/shared/service/comments.service";
import {SecurityService} from "app/shared/service/security.service";
import {isNil} from "lodash";
import {CommentFormComponent} from "app/shared/features/comments-panel/partials/comment-form/comment-form.component";

@Component({
	selector: 'app-comments-panel',
	standalone: true,
	imports: [
		CommentComponent,
		MatCard,
		MatCardHeader,
		MatCardTitle,
		MatCardContent,
		MatCardActions,
		MatPaginator,
		MatIcon,
		MatIconButton,
		NgIf,
		MatProgressSpinner,
		AsyncPipe
	],
	templateUrl: './comments-panel.component.html',
	styleUrl: './comments-panel.component.scss',
	animations: [fastAppearAnimation]
})
export class CommentsPanelComponent implements OnInit, OnDestroy {

	@Input() objectId!: number;
	@Input() commentType!: CommentType;

	isLoading = true;
	currentPage = 0;
	totalElements = 0;
	currentPageSize = 5;

	readonly availablePageSizes = [5, 10, 15];

	private _comments: Comment[] = [];

	constructor(private dialog: MatDialog,
				private commentsService: CommentsService,
				private securityService: SecurityService) {
	}

	ngOnInit(): void {
		this.downloadComments();
	}

	ngOnDestroy(): void {
	}

	addComment(): void {
		this.collectCommentContent().pipe(
			filter(content => !isNil(content)),
			take(1),
			tap(() => this.isLoading = true),
			switchMap(content => this.commentsService.createComment(content, this.objectId, this.commentType)),
		).subscribe(() => {
			this.currentPage = 0;
			this.downloadComments();
		});
	}

	editComment(commentId: number, commentContent: string) {
		this.isLoading = true;
		this.commentsService.editComment(commentId, commentContent, this.commentType).subscribe(() => {
			this.downloadComments();
		});
	}

	deleteComment(commentId: number) {
		this.isLoading = true;
		this.commentsService.deleteComment(commentId, this.commentType).subscribe(() => {
			this.downloadComments();
		});
	}

	replyComment(content: string, parentId: number) {
		this.isLoading = true;
		this.commentsService.createComment(content, this.objectId, this.commentType, parentId).subscribe(() => {
			this.downloadComments();
		});
	}

	handlePageEvent(event: PageEvent) {
		this.currentPageSize = event.pageSize;
		this.currentPage = event.pageIndex;
		this.downloadComments();
	}

	private collectCommentContent(): Observable<string> {
		const dialogRef = this.dialog.open(CommentFormComponent, {
			data: {
				mode: FormUsageMode.CREATE
			},
			minHeight: '10vh',
			minWidth: '70vh'
		});
		return dialogRef.afterClosed();
	}

	private downloadComments() {
		this.isLoading = true;
		this.commentsService.getComments(this.objectId, this.commentType, {
			page: this.currentPage,
			size: this.currentPageSize
		}).subscribe(result => {
			this.totalElements = result.totalElements || 0;
			this._comments = result.content;
			this.isLoading = false;
		});
	}

	get comments(): Comment[] {
		return this._comments;
	}

	get isUSerLoggedIn$(): Observable<boolean> {
		return this.securityService.isUserLoggedIn;
	}
}
