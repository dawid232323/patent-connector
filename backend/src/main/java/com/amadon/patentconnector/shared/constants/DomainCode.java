package com.amadon.patentconnector.shared.constants;

import com.fasterxml.jackson.annotation.JsonValue;

public enum DomainCode
{
	REGISTRATION( "REG" ),
	USER( "USR" ),
	VALIDATION( "VAL" ),
	LOGIN( "LOGIN" ),
	UNRECOGNISED( "UNRECOGNISED" );

	private final String code;

	DomainCode( final String aCode )
	{
		code = aCode;
	}

	@JsonValue
	public String getCode()
	{
		return code;
	}
}
