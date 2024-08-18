package com.amadon.patentconnector.shared.constants;

public record AppEndpoints( )
{
	private static final String base = "/api";
	public record UserEndpoints() {
		public static final String userBase = base + "/users";
		public static final String entrepreneurRegister = userBase + "/register";
	}
}
