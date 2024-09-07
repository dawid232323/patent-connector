export interface ErrorDialogData {
	title: string;
	errorMessage: string;
	uuid: string;
	errorCause?: string;
	additionalExceptionMessage?: string;
	statusCode?: number | string;
	acceptButtonMessage?: string;
}
