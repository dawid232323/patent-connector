export enum ConfirmationDialogAction {
	ACCEPT, DECLINE
}

export interface ConfirmationDialogData {
	title: string;
	message: string;
	declineLabel?: string;
	acceptLabel?: string;
}
