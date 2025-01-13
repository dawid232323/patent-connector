import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {CommentType} from "app/shared/types/comment.types";
import {CommentComponent} from "app/shared/features/comments-panel/partials/comment/comment.component";
import {MatCard, MatCardActions, MatCardContent, MatCardHeader, MatCardTitle} from "@angular/material/card";
import {MatPaginator} from "@angular/material/paginator";

@Component({
	selector: 'app-comments-panel',
	standalone: true,
	imports: [CommentComponent, MatCard, MatCardHeader, MatCardTitle, MatCardContent, MatCardActions, MatPaginator],
	templateUrl: './comments-panel.component.html',
	styleUrl: './comments-panel.component.scss'
})
export class CommentsPanelComponent implements OnInit, OnDestroy {

	@Input() objectId!: number;
	@Input() commentType!: CommentType;

	constructor() {
	}

	ngOnInit(): void {
	}

	ngOnDestroy(): void {
	}

}
