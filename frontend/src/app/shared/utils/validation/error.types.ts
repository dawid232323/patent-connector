export enum ErrorCode {
	REQUIRED = 'required',
	IDENTITY = 'identity',
	REGON = 'regon',
	EMAIL = 'email',
	MAX_LENGTH = 'maxlength',
	MIN_LENGTH = 'minlength',
	EXACT_LENGTH = 'exactLength',
	AT_LEAST_ONE_REQUIRED = 'oneRequired',
	FUTURE_DATE = 'futureDate',
	NON_EMPTY_ARRAY = 'nonEmptyArray'
}

export interface TextualValidationOptions {
	required?: boolean;
	maxLength?: number;
	minLength?: number;
}
