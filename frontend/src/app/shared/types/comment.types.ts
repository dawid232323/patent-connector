export enum CommentType {
	PATENT, EVENT
}

export interface Comment {
	id: number;
	parentId: number;
	authorName: string;
	authorEmail: string;
	authorLastName: string;
	authorAffiliation: string;
	content: string;
	createdAt: Date;
	replies: Comment[];
}
