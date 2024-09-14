import {HttpParams} from "@angular/common/http";
import {environment} from "src/environments/environment";


export interface ApiOptions {
	params?: HttpParams;
	headers?: Headers;
}


export class AppEndpoints {
	private static readonly base: string = environment.apiUrl;

	public static SecurityEndpoints = class {
		public static readonly securityBase: string = `${AppEndpoints.base}/security`;
		public static readonly login: string = `${this.securityBase}/login`;
		public static readonly refreshToken: string = `${this.securityBase}/refresh-token`;
	};

	public static UserEndpoints = class {
		public static readonly userBase: string = `${AppEndpoints.base}/users`;
		public static readonly entrepreneurRegister: string = `${this.userBase}/register`;
		public static readonly researchInstitutionRegister: string = `${this.userBase}/register-research-institution-worker`;
		public static readonly setInitialPassword: string = `${this.userBase}/set-initial-password`;
		public static readonly myDetails: string = `${this.userBase}/me`;
		public static readonly updateBusinessBranches = `${this.userBase}/update-business-branches`;
	};

	public static ResearchInstitutionEndpoints = class {
		public static readonly resInstitutionBase: string = `${AppEndpoints.base}/research-institutions`;
		public static readonly resInstitutionFind: string = `${this.resInstitutionBase}/search`;
	};

	public static BusinessBranchEndpoints = class {
		public static readonly businessBranchBase: string = `${AppEndpoints.base}/business-branches`;
		public static readonly sectionBusinessBranches: string = `${this.businessBranchBase}/section-business-branches`;
	};

	public static getExcludedEndpoints(): string[] {
		return [
			AppEndpoints.UserEndpoints.entrepreneurRegister,
			AppEndpoints.UserEndpoints.setInitialPassword,
			AppEndpoints.UserEndpoints.researchInstitutionRegister,
			AppEndpoints.SecurityEndpoints.login,
			AppEndpoints.SecurityEndpoints.refreshToken,
			AppEndpoints.BusinessBranchEndpoints.sectionBusinessBranches,
			AppEndpoints.ResearchInstitutionEndpoints.resInstitutionFind
		];
	}
}
