package com.amadon.patentconnector.user.entity;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Arrays;

@Getter
public enum UserRole implements GrantedAuthority
{
	ENTREPRENEUR( "ENTREPRENEUR" ),
	RESEARCH_WORKER( "RESEARCH_WORKER" ),
	ADMIN( "ADMIN" );

	private final String authority;

	UserRole( final String aAuthority )
	{
		authority = aAuthority;
	}

	public static UserRole fromString( final String aAuthority )
	{
		return Arrays.stream( values() )
				.filter( aUserRole -> aUserRole.getAuthority()
						.equals( aAuthority ) )
				.findFirst()
				.orElseThrow( IllegalArgumentException::new );
	}
}
