package com.amadon.patentconnector.shared.util.token;

import com.amadon.patentconnector.user.entity.User;
import com.amadon.patentconnector.user.service.UserService;
import com.amadon.patentconnector.user.service.exception.UserRegistrationException;
import io.jsonwebtoken.*;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
class JWTValidator
{

	private final UserService userService;

	@Nullable
	public String getUserEmailIfAuthTokenIsValid( final String aToken, final Key aSigningKey )
	{
		if ( isAuthTokenValid( aToken, aSigningKey ) )
		{
			final JwtParser parser = getTokenParser( aSigningKey );
			final Claims parsedClaims = getTokenClaims( aToken, parser );
			return getUserEmail( parsedClaims );
		}
		return null;
	}

	public boolean isAuthTokenValid( final String aToken, final Key aSigningKey )
	{
		final JwtParser parser = getTokenParser( aSigningKey );
		final Claims parsedClaims = getTokenClaims( aToken, parser );
		final String userEmail = getUserEmail( parsedClaims );

		if ( isTokenExpired( parsedClaims ) )
		{
			log.warn( "Found expired token for user {}. Token date is {}", userEmail, parsedClaims.getExpiration() );
			return false;
		}
		final User user = userService.tryToFindByEmail( userEmail )
				.orElseThrow( () -> new UsernameNotFoundException( "Could not find user with " +
																		   "login " + userEmail ) );
		if ( !user.getIsActive() )
		{
			log.warn( "Inactive user {} tryied to authenticate", user.getEmail() );
			return false;
		}
		if ( !user.getSecretKey()
				.equals( getUserSecret( parsedClaims ) ) )
		{
			log.warn( "Could not compare secret key for user {}. Checked {} against {}", userEmail,
					  getUserSecret( parsedClaims ), user.getSecretKey() );
			return false;
		}
		return true;
	}

	public String validateAndGetUserEmailFromRegistrationToken( final String aSecretTokenString, final Key aSigningKey )
	{
		final JwtParser parser = getTokenParser( aSigningKey );
		final Claims parsedClaims = getTokenClaims( aSecretTokenString, parser );
		final String userEmail = getUserEmail( parsedClaims );

		if ( isTokenExpired( parsedClaims ) )
		{
			log.warn( "Found expired token for user {}. Token date is {}", userEmail, parsedClaims.getExpiration() );
			throw new UserRegistrationException( "Given secret token is expired, please contact system admin" );
		}
		final User user = userService.tryToFindByEmail( userEmail )
									 .orElseThrow( () -> new UsernameNotFoundException( "Could not find user with " +
																								"login " + userEmail ) );
		if ( !user.getSecretKey()
				  .equals( getUserSecret( parsedClaims ) ) )
		{
			log.warn( "Could not compare secret key for user {}. Checked {} against {}", userEmail,
					  getUserSecret( parsedClaims ), user.getSecretKey() );
			throw new UserRegistrationException( "Encoded secret key does not match the saved one. Please contact " +
														 "system administrator" );
		}
		return userEmail;
	}

	private JwtParser getTokenParser( final Key aSigningKey )
	{
		return Jwts.parser()
				   .verifyWith( (SecretKey) aSigningKey )
				   .build();
	}

	private Claims getTokenClaims( final String aToken, final JwtParser aParser )
	{
		Claims parsedClaims;
		try
		{
			parsedClaims = aParser.parseSignedClaims( aToken )
					.getPayload();
		} catch ( Exception aE )
		{
			log.error( "Exception occurred during parsing JWT. Original cause is", aE );
			throw new RuntimeException( aE );
		}
		return parsedClaims;
	}

	private boolean isTokenExpired( final Claims aTokenClaims )
	{
		return aTokenClaims.getExpiration()
						   .before( new Date() );
	}

	private String getUserEmail( final Claims aTokenClaims )
	{
		return aTokenClaims.getSubject();
	}

	private String getUserSecret( final Claims aTokenClaims )
	{
		return aTokenClaims.get( JWTGenerator.USER_SECRET_CLAIM_KEY, String.class );
	}
}
