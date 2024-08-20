package com.amadon.patentconnector.shared.util.token;

import com.amadon.patentconnector.user.entity.User;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Objects;

@Slf4j
@Service
public class JWTService
{
	private final String SECRET_KEY;
	private final JWTGenerator tokenGenerator;
	private final JWTValidator tokenValidator;

	public JWTService( @Value( "${spring.application.jwt_secret_key}" ) final String aSecretKey,
					   final JWTGenerator aGenerator, final JWTValidator aValidator )
	{
		SECRET_KEY = aSecretKey;
		tokenGenerator = aGenerator;
		tokenValidator = aValidator;
	}

	public String generateTokenForUserAuthentication( final User aUser, final boolean isRefresh )
	{
		return tokenGenerator.generateTokenForAuthentication( aUser, isRefresh, getSigningKey() );
	}

	public String generateTokenForUserRegistration( final String aUserEmail, final String aUserSecret )
	{
		return tokenGenerator.generateTokenForUserRegistration( aUserEmail, aUserSecret, getSigningKey() );
	}

	public String getRegisteredUserEmailFromToken( final String aSecretToken )
	{
		return tokenValidator.validateAndGetUserEmailFromRegistrationToken( aSecretToken, getSigningKey() );
	}

	public String getTokenFromAuthorizationHeader( final String aAuthorizationHeader )
	{
		if ( Objects.isNull( aAuthorizationHeader ) || !aAuthorizationHeader.startsWith( "Bearer" ) )
		{
			log.info( "Token is null or doesn't meet the desired authentication method" );
			return null;
		}
		return aAuthorizationHeader.substring( 7 );
	}

	public String getUserEmailIfAuthTokenIsValid( final String aAuthorizationHeader )
	{
		final String token = getTokenFromAuthorizationHeader( aAuthorizationHeader );
		return tokenValidator.getUserEmailIfAuthTokenIsValid( token, getSigningKey() );
	}

	public boolean isAuthorizationTokenValid( final String aAuthorizationHeader )
	{
		final String token = getTokenFromAuthorizationHeader( aAuthorizationHeader );
		if ( Objects.isNull( token ) )
		{
			return false;
		}
		return tokenValidator.isAuthTokenValid( token, getSigningKey() );
	}

	private Key getSigningKey()
	{
		final byte[] secretBytes = Decoders.BASE64.decode( SECRET_KEY );
		return Keys.hmacShaKeyFor( secretBytes );
	}
}
