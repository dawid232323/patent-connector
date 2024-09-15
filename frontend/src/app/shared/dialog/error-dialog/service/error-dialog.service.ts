import {Injectable} from '@angular/core';
import {MatDialog, MatDialogConfig, MatDialogRef} from "@angular/material/dialog";
import {DomainCode, domainCodeTitle, ErrorResponse} from "app/shared/types/error.types";
import {ErrorDialogComponent} from "app/shared/dialog/error-dialog/error-dialog.component";
import {ErrorDialogData} from "app/shared/dialog/error-dialog/types/error-dialog.types";
import {isNil} from "lodash";
import {HttpErrorResponse} from "@angular/common/http";
import { v4 as uuid } from 'uuid';

@Injectable({
	providedIn: 'root'
})
export class ErrorDialogService {

	constructor(private dialog: MatDialog) {
	}

	openHttpResponseError(errorResponse: ErrorResponse): MatDialogRef<ErrorDialogComponent> {
		const initialData = this.getDialogDataFromHttpError(errorResponse);
		return this.dialog.open(ErrorDialogComponent, {
			data: initialData,
			...this.resolveMatDialogConfig()
		});
	}

	openDefaultErrorResponse(error: HttpErrorResponse): MatDialogRef<ErrorDialogComponent> {
		const initialData = this.getDialogDataFromHttpErrorResponse(error);
		return this.dialog.open(ErrorDialogComponent, {
			data: initialData,
			...this.resolveMatDialogConfig()
		});
	}

	openApplicationError(error: Error) {
		const initialData = this.getDialogDataFromAppError(error);
		return this.dialog.open(ErrorDialogComponent, {
			data: initialData,
			...this.resolveMatDialogConfig()
		});
	}

	private getDialogDataFromHttpError(errorResponse: ErrorResponse): ErrorDialogData {
		const title = this.getDomainCodeTitle(errorResponse.domainCode);
		return {
			title: title,
			errorMessage: errorResponse.message,
			uuid: errorResponse.uuid,
			errorCause: errorResponse.errorCause,
			additionalExceptionMessage: errorResponse.originalExceptionMessage,
			statusCode: errorResponse.status,
			acceptButtonMessage: this.getAcceptButtonMessage(errorResponse.domainCode)
		}
	}

	private getDialogDataFromHttpErrorResponse(response: HttpErrorResponse): ErrorDialogData {
		return {
			title: 'Wystąpił nieoczekiwany błąd',
			errorMessage: response.message,
			statusCode: response.status,
			uuid: uuid()
		}
	}

	private getDialogDataFromAppError(error: Error): ErrorDialogData {
		return {
			title: 'Wystąpił błąd aplikacji',
			errorMessage: error.message,
			uuid: uuid(),
			additionalExceptionMessage: String(error.cause) || undefined
		}
	}

	private getDomainCodeTitle(domainCode: DomainCode): string {
		if (isNil(domainCodeTitle[domainCode])) {
			return 'Wystąpił nieoczekiwany błąd';
		}
		return domainCodeTitle[domainCode];
	}

	private getAcceptButtonMessage(domainCode: DomainCode): string | undefined {
		if (domainCode === DomainCode.REGISTRATION) {
			return 'Popraw dane i spróbuj jeszcze raz';
		}
		return undefined;
	}

	private resolveMatDialogConfig(): MatDialogConfig<any> {
		return {
			minHeight: '10vh',
			minWidth: '70vh'
		}
	}
}
