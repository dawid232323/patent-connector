package com.amadon.patentconnector.shared.util.token;

import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

@Service
@RequiredArgsConstructor
class JWTGenerator
{
	private final String USER_SECRET_CLAIM_KEY = "USER_SECRET";

	public String generateTokenForUserRegistration( final String aUserEmail, final String aUserSecret,
													final Key aSigningKey )
	{
		final Map< String, Object > claimsMap = Map.of( USER_SECRET_CLAIM_KEY, aUserSecret );
		return generateToken( claimsMap, aUserEmail, aSigningKey );
	}

	private String generateToken( final Map< String, Object > aClaims, final String aSubject, final Key aSigningKey )
	{
		return generateToken( aClaims, aSubject, new Date( Long.MAX_VALUE ), aSigningKey );
	}

	private String generateToken( final Map< String, Object > aClaims, final String aSubject,
								  final Date aExpirationDate, final Key aSigningKey )
	{
		return Jwts.builder()
				.claims( aClaims )
				.subject( aSubject )
				.issuedAt( new Date() )
				.expiration( aExpirationDate )
				.signWith( aSigningKey )
				.compact();
	}
}
