package com.amadon.patentconnector.shared.util.token;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

@Service
public class JWTGenerator
{
	private final String SECRET_KEY;
	private final String USER_SECRET_CLAIM_KEY = "USER_SECRET";

	public JWTGenerator( @Value( "${spring.application.jwt_secret_key}" ) final String aSecretKey )
	{
		SECRET_KEY = aSecretKey;
	}

	public String generateTokenForUserRegistration( final String aUserEmail, final String aUserSecret )
	{
		final Map< String, Object > claimsMap = Map.of( USER_SECRET_CLAIM_KEY, aUserSecret );
		return generateToken( claimsMap, aUserEmail );
	}

	private String generateToken( final Map< String, Object > aClaims, final String aSubject )
	{
		return Jwts.builder()
				.claims( aClaims )
				.subject( aSubject )
				.issuedAt( new Date() )
				.expiration( Date.from( Instant.MAX ) )
				.signWith( getSigningKey() )
				.compact();
	}

	private Key getSigningKey()
	{
		final byte[] secretBytes = Decoders.BASE64.decode( SECRET_KEY );
		return Keys.hmacShaKeyFor( secretBytes );
	}
}
