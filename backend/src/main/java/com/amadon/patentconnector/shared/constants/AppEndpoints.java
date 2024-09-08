package com.amadon.patentconnector.shared.constants;

import java.util.List;

public record AppEndpoints( )
{
	private static final String base = "/api";

	public record SecurityEndpoints( )
	{
		public static final String securityBase = base + "/security";
		public static final String login = "/login";
		public static final String refreshToken = "/refresh-token";
	}

	public record UserEndpoints( )
	{
		public static final String userBase = base + "/users";
		public static final String entrepreneurRegister = "/register";
		public static final String researchInstitutionRegister = "/register-research-institution-worker";
		public static final String setInitialPassword = "/set-initial-password";
		public static final String myDetails = "/me";
	}

	public record ResearchInstitutionEndpoints( )
	{
		public static final String resInstitutionBase = base + "/research-institutions";
		public static final String resInstitutionFind = "/search";
	}

	public record BusinessBranchEndpoints()
	{
		public static final String businessBranchBase = base + "/business-branches";
		public static final String sectionBusinessBranches = "/section-business-branches";
	}

	public static List< String > getExcludedEndpoints()
	{
		return List.of(
				UserEndpoints.userBase.concat( UserEndpoints.entrepreneurRegister ),
				UserEndpoints.userBase.concat( UserEndpoints.setInitialPassword ),
				UserEndpoints.userBase.concat( UserEndpoints.researchInstitutionRegister ),
				SecurityEndpoints.securityBase.concat( SecurityEndpoints.login ),
				SecurityEndpoints.securityBase.concat( SecurityEndpoints.refreshToken.concat( "/**" ) ),
				BusinessBranchEndpoints.businessBranchBase.concat( BusinessBranchEndpoints.sectionBusinessBranches ),
				ResearchInstitutionEndpoints.resInstitutionBase.concat( ResearchInstitutionEndpoints.resInstitutionFind.concat( "/**" ) )
					  );
	}
}
