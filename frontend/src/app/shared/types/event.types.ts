import {User} from "app/shared/types/user.types";
import {BusinessBranch} from "app/shared/types/business-branch.types";

export interface PersistEvent {
  title: string;
  startDate: Date;
  endDate: Date;
  localization: string;
  description: string;
  contactEmail: string;
  contactPhone: string;
  registrationDetails: string;
  businessBranchesIds: number[];
}

export interface Event {
	id: number;
	title: string;
	startDate: Date;
	caregiver: User;
	description: string;
	registrationDetails: string;
	createdAt: Date;
	updatedAt: Date;
	businessBranches: BusinessBranch[];
	endDate: Date | null;
	localization?: string;
	contactEmail?: string;
	contactPhone?: string;
}

export interface EventSearchQuery {
	page: number;
	size: number;
	title?: string;
	dateFrom?: Date | number | string;
	dateTo?: Date | number | string;
	sectionBranchesCodes?: string[];
	caregiverId?: number;
}
