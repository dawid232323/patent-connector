package com.amadon.patentconnector.shared.util.token;

import com.amadon.patentconnector.user.entity.User;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Map;

@Service
@RequiredArgsConstructor
class JWTGenerator
{
	static final String USER_SECRET_CLAIM_KEY = "USER_SECRET";
	private static final Duration tokenDuration = Duration.ofMinutes( 5 );
	private static final Duration refreshTokenDuration = Duration.ofHours( 10 );

	public String generateTokenForAuthentication( final User aUser, final boolean isRefresh, final Key aSigningKey )
	{
		//TODO when roles are introduced add user roles to claims
		final Map< String, Object > claimsMap = Map.of( USER_SECRET_CLAIM_KEY, aUser.getSecretKey() );
		final Date expirationDate = getExpirationDate( isRefresh );
		return generateToken( claimsMap, aUser.getEmail(), expirationDate, aSigningKey );
	}

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

	private Date getExpirationDate( final boolean isRefresh )
	{
		final LocalDateTime now = LocalDateTime.now();
		if ( isRefresh )
		{
			return Date.from( now.plus( refreshTokenDuration ).toInstant( ZoneOffset.UTC ) );
		}
		return Date.from( now.plus( tokenDuration ).toInstant( ZoneOffset.UTC ) );
	}
}
