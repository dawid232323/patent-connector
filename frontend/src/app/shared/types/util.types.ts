import {AbstractControl} from "@angular/forms";
import {ErrorCode} from "app/shared/utils/validation/error.types";
import {Toolbar} from "ngx-editor";


export const RichTextEditorToolbarOptions: Toolbar = [
	["bold", "italic"],
	["underline", "strike"],
	["code", "blockquote"],
	["ordered_list", "bullet_list"],
	[{ heading: ["h1", "h2", "h3", "h4", "h5", "h6"] }],
	["text_color"],
	["align_left", "align_center", "align_right", "align_justify"],
];

export enum FormUsageMode {
	CREATE, EDIT
}

export interface ValidatedForm {

	readonly errorCode: typeof ErrorCode;

	getControlErrorType(control: AbstractControl): string | null;

	validateAndSubmit(): void;
}
