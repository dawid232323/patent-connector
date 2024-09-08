import {ErrorHandler, inject} from "@angular/core";
import {ErrorDialogService} from "app/shared/dialog/error-dialog/service/error-dialog.service";
import {HttpErrorResponse} from "@angular/common/http";
import {isNil} from "lodash";
import {ErrorResponse} from "app/shared/types/error.types";

export function globalErrorHandler(): ErrorHandler {
	const errorDialogService = inject(ErrorDialogService);
	return {
		handleError(error: any) {
			console.error(error);
			if (isHttpErrorResponse(error)) {
				handleHttpErrorResponse(error, errorDialogService);
			} else {
				handleDefaultError(error, errorDialogService);
			}
		}
	}
}

function isHttpErrorResponse(error: any): error is HttpErrorResponse {
	return !isNil(error.name) && error.name === 'HttpErrorResponse';
}

function isErrorResponse(body: any): body is ErrorResponse {
	return !isNil(body.domainCode) && !isNil(body.status);
}

function handleHttpErrorResponse(error: HttpErrorResponse, service: ErrorDialogService) {
	if (isErrorResponse(error.error)) {
		service.openHttpResponseError(error.error);
	} else {
		service.openDefaultErrorResponse(error);
	}
}

function handleDefaultError(error: Error, service: ErrorDialogService) {
	service.openApplicationError(error);
}
