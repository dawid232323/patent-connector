import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {CommentType} from "app/shared/types/comment.types";
import {CommentComponent} from "app/shared/features/comments-panel/partials/comment/comment.component";

@Component({
	selector: 'app-comments-panel',
	standalone: true,
	imports: [CommentComponent],
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
