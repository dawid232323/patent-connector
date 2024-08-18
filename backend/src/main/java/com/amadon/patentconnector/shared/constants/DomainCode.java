package com.amadon.patentconnector.shared.constants;

import com.fasterxml.jackson.annotation.JsonValue;

public enum DomainCode
{
	REGISTRATION( "REG" ),
	USER( "USR" );

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
