package com.amadon.patentconnector.user.service.exception;

public class UserNotFoundException extends RuntimeException
{
	public UserNotFoundException( final String aMessage )
	{
		super( aMessage );
	}

	public UserNotFoundException( final Long aUserId )
	{
		super( String.format( "User with id %d does not exist", aUserId.intValue() ) );
	}
}
