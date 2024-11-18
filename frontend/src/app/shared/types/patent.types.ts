export interface PatentSearchQuery {
	title: string;
	businessBranchesIds: number[];
	dateCreated: Date;
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
