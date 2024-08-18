package com.amadon.patentconnector.shared.util.hash;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Service
public class HashGenerator
{
	private final String SALT;

	public HashGenerator( @Value( "${spring.application.hash_salt}" ) final String aSalt )
	{
		SALT = aSalt;
	}

	public String generateHashFromString( final String aString )
	{
		final String valueToHash = getStringWithSalt( aString );
		return hashUsingSHA256( valueToHash );
	}

	private String getStringWithSalt( final String aString )
	{
		final String base64Salt = Base64.getEncoder()
				.encodeToString( SALT.getBytes( StandardCharsets.UTF_8 ) );
		return base64Salt.concat( aString );
	}

	private String hashUsingSHA256( final String aData )
	{
		try
		{
			MessageDigest digest = MessageDigest.getInstance( "SHA-256" );
			byte[] hashBytes = digest.digest( aData.getBytes() );
			return Base64.getEncoder()
					.encodeToString( hashBytes );
		} catch ( NoSuchAlgorithmException e )
		{
			throw new RuntimeException( e );
		}
	}
}
