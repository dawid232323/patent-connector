import {Injectable} from '@angular/core';
import {ApiService} from "app/shared/service/api.service";
import {Comment, CommentType, CreateComment} from "app/shared/types/comment.types";
import {AppEndpoints, PagedResult, PageOptions} from "app/shared/types/api.types";
import {Observable} from "rxjs";

@Injectable({
	providedIn: 'root'
})
export class CommentsService {

	constructor(private apiService: ApiService) {
	}

	getComments(objectId: number, commentType: CommentType, options?: PageOptions): Observable<PagedResult<Comment>> {
		const url = this.resolveUrlBase(commentType);
		return this.apiService.get<PagedResult<Comment>>(url.concat(`/${objectId}`), {params: options});
	}

	createComment(content: string, objectId: number, commentType: CommentType, parentId?: number): Observable<Comment> {
		const body = this.resolveCommentBody(content, objectId, commentType, parentId);
		const url = this.resolveUrlBase(commentType);
		return this.apiService.post<CreateComment, Comment>(body, url);
	}

	editComment(commentId: number, commentContent: string, commentType: CommentType): Observable<Comment> {
		const url = this.resolveUrlBase(commentType).concat(`/${commentId}`);
		const body = {content: commentContent};
		return this.apiService.put(url, body);
	}

	deleteComment(commentId: number, commentType: CommentType): Observable<void> {
		const url = this.resolveUrlBase(commentType).concat(`/${commentId}`);
		return this.apiService.delete(url);
	}

	private resolveUrlBase(commentType: CommentType): string {
		switch (commentType) {
			case CommentType.EVENT:
				return AppEndpoints.CommentEndpoints.eventComments;
			default:
			case CommentType.PATENT:
				return AppEndpoints.CommentEndpoints.patentComments;
		}
	}

	private resolveCommentBody(content: string, objectId: number, commentType: CommentType, parentId?: number): CreateComment {
		return {
			content: content,
			parentId: parentId || null,
			patentId: commentType === CommentType.PATENT ? Number(objectId) : null,
			eventId: commentType === CommentType.EVENT ? Number(objectId) : null,
		}
	}
}
