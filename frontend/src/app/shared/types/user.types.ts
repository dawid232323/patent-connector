import {EntrepreneursDataDto} from "app/features/common/features/register/types/registration.types";

export enum UserRole {
	ENTREPRENEUR = 'ENTREPRENEUR',
	RESEARCH_WORKER = 'RESEARCH_WORKER',
	ADMIN = 'ADMIN'
}

export interface User {
	id: number;
	email: string;
	firstName: string;
	lastName: string;
	isActive: boolean;
	roles: UserRole[];
	entrepreneursData?: EntrepreneursDataDto;
	// TODO define when model is introduced
	researchInstitution?: any;
}