import {HttpParams} from "@angular/common/http";
import {environment} from "src/environments/environment";


export interface ApiOptions {
	params?: HttpParams;
	headers?: Headers;
}

export interface PagedResult <T> {
	number: number,
	size: number,
	totalElements?: number,
	content: T[]
}

export interface PageOptions {
	page: number; // page number
	size: number; // page size
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
		public static readonly researchInstitutionWorkers = `${this.userBase}/research-institution-workers`;
	};

	public static ResearchInstitutionEndpoints = class {
		public static readonly resInstitutionBase: string = `${AppEndpoints.base}/research-institutions`;
		public static readonly resInstitutionFind: string = `${this.resInstitutionBase}/search`;
	};

	public static BusinessBranchEndpoints = class {
		public static readonly businessBranchBase: string = `${AppEndpoints.base}/business-branches`;
		public static readonly sectionBusinessBranches: string = `${this.businessBranchBase}/section-business-branches`;
		public static readonly specificBusinessBranches: string = `${this.businessBranchBase}/specific-business-branches`;
	};

	public static PatentEndpoints = class {
		public static readonly patentsBase: string = `${AppEndpoints.base}/patents`;
		public static readonly patentsSearch: string = `${this.patentsBase}/search`;
	}

	public static EventEndpoints = class {
		public static readonly eventsBase: string = `${AppEndpoints.base}/events`;
		public static readonly eventsSearch: string = `${this.eventsBase}/listing`;
	}

	public static CommentEndpoints = class {
		public static readonly commentsBase: string = `${AppEndpoints.base}/comments`;
		public static readonly patentComments: string = `${this.commentsBase}/patent-comments`;
		public static readonly eventComments: string = `${this.commentsBase}/event-comments`;
	}

	public static InventionDemands = class {
		public static readonly inventionDemandsBase: string = `${AppEndpoints.base}/invention-demands`;
	}

	public static getExcludedEndpoints(): string[] {
		return [
			AppEndpoints.UserEndpoints.entrepreneurRegister,
			AppEndpoints.UserEndpoints.setInitialPassword,
			AppEndpoints.UserEndpoints.researchInstitutionRegister,
			AppEndpoints.SecurityEndpoints.login,
			AppEndpoints.SecurityEndpoints.refreshToken,
			AppEndpoints.BusinessBranchEndpoints.sectionBusinessBranches,
			AppEndpoints.ResearchInstitutionEndpoints.resInstitutionFind,
			AppEndpoints.PatentEndpoints.patentsSearch
		];
	}
}
