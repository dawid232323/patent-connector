import {CreateEntrepreneursDataDto} from "app/features/common/features/register/types/registration.types";
import {ResearchInstitution} from "app/shared/types/research-institution.types";
import {BusinessBranch} from "app/shared/types/business-branch.types";

export enum UserRole {
	ENTREPRENEUR = 'ENTREPRENEUR',
	RESEARCH_WORKER = 'RESEARCH_WORKER',
	ADMIN = 'ADMIN'
}

export interface EntrepreneursDataDto extends CreateEntrepreneursDataDto {
	interestBranches: BusinessBranch[];
}

export interface User {
	id: number;
	email: string;
	firstName: string;
	lastName: string;
	isActive: boolean;
	roles: UserRole[];
	entrepreneursData?: EntrepreneursDataDto;
	researchInstitution?: ResearchInstitution;
}
