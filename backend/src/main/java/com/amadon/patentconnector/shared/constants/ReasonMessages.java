package com.amadon.patentconnector.shared.constants;

public record ReasonMessages( )
{
	public static final String INVALID_DATA = "Provided data was invalid or didn't meet the requirements";
	public static final String USER_DOES_NOT_EXIST = "User with specified email does not exist";
	public static final String LOGIN_ERROR = "Login process did not finished correctly";
	public static final String ROLE_MISMATCH = "User does not have necessary role to perform this operation";
	public static final String UNEXPECTED_ERROR = "Unexpected server error occurred";
	public static final String ENTITY_NOT_FOUND = "Requested entity could not be found";
	public static final String FORBIDDEN = "Access for current user is forbidden";
	public static final String EMAIL_FAILURE = "Email could not be sent. PLease refer logs for more details";
}
