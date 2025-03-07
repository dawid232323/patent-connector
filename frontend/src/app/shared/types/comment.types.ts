import {FormUsageMode} from "app/shared/types/util.types";

export enum CommentType {
	PATENT, EVENT
}

export interface Comment {
	id: number;
	parentId: number | null;
	authorName: string;
	authorEmail: string;
	authorLastName: string;
	authorAffiliation: string;
	content: string;
	createdAt: Date;
	replies: Comment[];
}

export interface CommentFormInitialData {
	mode: FormUsageMode;
	content?: string;
}

export interface CreateComment {
	content: string;
	parentId: number | null;
	patentId: number | null;
	eventId: number | null;
}
