export interface CreateEntrepreneursDataDto {
	nip?: string;
	regon?: string;
	companyName?: string;
}

export interface CreateEntrepreneurDto {
	email: string;
	firstName: string;
	lastName: string;
	entrepreneursData: CreateEntrepreneursDataDto;
}
