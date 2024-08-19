package com.amadon.patentconnector.user.service.exception;

public class UserRegistrationException extends RuntimeException
{
	public UserRegistrationException( final String aMessage )
	{
		super( aMessage );
	}

	public UserRegistrationException( final String aMessage, final Throwable aCause )
	{
		super( aMessage, aCause );
	}
}
