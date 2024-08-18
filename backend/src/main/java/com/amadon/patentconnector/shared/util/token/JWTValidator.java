package com.amadon.patentconnector.shared.util.token;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;

@Slf4j
@Service
class JWTValidator
{
	// TODO extend this functionality when login is introduced
	public boolean isTokenValid( final String aToken, final Key aSigningKey )
	{
		final JwtParser parser = getTokenParser( aSigningKey );
		try
		{
			parser.parse( aToken );
			return true;
		} catch ( Exception aE )
		{
			log.error( "Exception occurred during parsing JWT. Original cause is", aE );
		}
		return false;
	}

	private JwtParser getTokenParser( final Key aSigningKey )
	{
		return Jwts.parser()
				.verifyWith( (SecretKey) aSigningKey )
				.build();
	}
}
