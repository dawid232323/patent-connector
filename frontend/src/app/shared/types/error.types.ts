export enum DomainCode {
	REGISTRATION = 'REG',
	USER = 'USR',
	VALIDATION = 'VAL',
	LOGIN = 'LOGIN'
}

export const domainCodeTitle: Record<DomainCode, string> = {
	REG: 'Wystąpił błąd rejestracji',
	USR: 'Wystąpił błąd użytkownika',
	VAL: 'Wystąpił błąd walidacji',
	LOGIN: 'Wystąpił błąd logowania'
};

export interface ErrorResponse {
	status: string;
	errorTitle: string;
	message: string;
	domainCode: DomainCode;
	errorCause: string;
	uuid: string;
	originalExceptionMessage: string;
}
