export type PatentUsageDescriptions = { [branchCode: string]: string[] };

export interface PatentSearchQuery {
	number: number;
	size: number;
	title?: string;
	businessBranchesIds?: number[];
	dateCreated?: Date | number | string;
}

export interface PatentSearchResult {
	id: number;
	name: string;
	patentAbstract: string;
	dateFrom: Date;
	patentTimestamp: Date;
	statusDescription: string;
	businessBranches: string[];
}


export interface PatentCitation {
	documentNumber: string;
	documentName: string;
	citationText: string;
	publicationDate: string;
}

export interface PatentDocument {
	documentCode: string;
	documentUri: string;
}

export interface PatentAddressBook {
	name?: string;
	firstName?: string;
	lastName?: string;
	organisationName?: string;
	postalCode?: string;
	country?: string;
	city?: string;
	nationality?: string;
}

export interface Patent {
	title: string;
	description: string;
	abstractField: string;
	businessBranches: string[];
	citations: PatentCitation[];
	beginDate: Date;
	endDate: Date;
	statusDescription: string;
	patentNumber: string;
	documents: PatentDocument[];
	agents: PatentAddressBook[];
	applicants: PatentAddressBook[];
	assignees: PatentAddressBook[];
	inventors: PatentAddressBook[];
	usageDescriptions: PatentUsageDescriptions;
}
