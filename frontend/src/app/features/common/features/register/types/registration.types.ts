export interface CreateEntrepreneursDataDto {
	nip?: string;
	regon?: string;
	companyName?: string;
	recommendationAgreement?: boolean;
}

export interface CreateEntrepreneurDto {
	email: string;
	firstName: string;
	lastName: string;
	entrepreneursData: CreateEntrepreneursDataDto;
}
