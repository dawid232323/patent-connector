package com.amadon.patentconnector.shared.constants;

import java.util.List;

public record AppEndpoints( )
{
	private static final String base = "/api";

	public record UserEndpoints() {
		public static final String userBase = base + "/users";
		public static final String entrepreneurRegister = "/register";
		public static final String setInitialPassword = "/set-initial-password";
	}

	public static List< String > getExcludedEndpoints()
	{
		return List.of(
			UserEndpoints.userBase.concat( UserEndpoints.entrepreneurRegister ),
			UserEndpoints.userBase.concat( UserEndpoints.setInitialPassword )
		);
	}
}
