export interface ResearchInstitutionRegisterPreview {
	id: number;
	name: string;
	address: string;
}

export enum AvailableInstitutionResult {
	NONE,
	SINGLE,
	MULTIPLE
}

export interface ResearchInstitutionWorkerCreateDto {
	email: string;
	firstName: string;
	lastName: string;
	institutionId: number;
}
