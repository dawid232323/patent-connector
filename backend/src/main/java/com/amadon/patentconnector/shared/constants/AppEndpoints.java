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
		public static final String updateBusinessBranches = "update-business-branches";
		public static final String researchInstitutionWorkers = "research-institution-workers";
	}

	public record ResearchInstitutionEndpoints( )
	{
		public static final String resInstitutionBase = base + "/research-institutions";
		public static final String resInstitutionFind = "/search";
		public static final String registeredResInstitution = "registered-research-institution";
	}

	public record BusinessBranchEndpoints()
	{
		public static final String businessBranchBase = base + "/business-branches";
		public static final String sectionBusinessBranches = "/section-business-branches";
		public static final String specificBusinessBranches = "/specific-business-branches";
		public static final String findBranchChildren = "/find-branch-children";
	}

	public record PatentEndpoints() {
		public static final String patentBase = base + "/patents";
		public static final String uploadPatents = "/upload";
		public static final String patentFind = "/search";
	}

	public record InventionDemandEndpoints( ) {
		public static final String inventionDemandBase = base + "/invention-demands";
	}

	public record EventEndpoints( ) {
		public static final String eventBase = base + "/events";
		public static final String eventFind = "/search";
	}

	public record CommentEndpoints( ) {
		public static final String commentBase = base + "/comments";
		public static final String patentComments = "/patent-comments";
		public static final String eventComments = "/event-comments";
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
				BusinessBranchEndpoints.businessBranchBase.concat( BusinessBranchEndpoints.specificBusinessBranches ),
				BusinessBranchEndpoints.businessBranchBase.concat( BusinessBranchEndpoints.findBranchChildren.concat( "/**" ) ),
				ResearchInstitutionEndpoints.resInstitutionBase.concat( ResearchInstitutionEndpoints.resInstitutionFind.concat( "/**" ) ),
				PatentEndpoints.patentBase.concat( PatentEndpoints.uploadPatents ),
				PatentEndpoints.patentBase.concat( PatentEndpoints.patentFind ),
				PatentEndpoints.patentBase.concat( "/**" ),
				EventEndpoints.eventBase.concat( "/**" ),
				CommentEndpoints.commentBase.concat( "/**" )
					  );
	}
}
