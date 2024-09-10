package com.amadon.patentconnector.user.service.exception;

import com.amadon.patentconnector.user.entity.UserRole;

public class RoleMismatchException extends RuntimeException
{
	public RoleMismatchException( final String aUserEmail, final UserRole aDesiredRole )
	{
		super( resolveExceptionMessage( aUserEmail, aDesiredRole ) );
	}

	private static String resolveExceptionMessage( final String aUserEmail, final UserRole aRole )
	{
		return String.format( "User %s doesn't have role %s, required to perform this operation", aUserEmail,
							  aRole.getAuthority() );
	}
}
