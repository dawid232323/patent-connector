package com.amadon.patentconnector.user.service.validator.impl;

import com.amadon.patentconnector.user.features.entrepreneurData.service.dto.CreateEntrepreneursDataDto;
import com.amadon.patentconnector.user.service.dto.CreateUserDto;
import com.amadon.patentconnector.user.service.exception.UserRegistrationException;
import com.amadon.patentconnector.user.service.validator.CreateEntrepreneurUserValidationRule;
import org.springframework.stereotype.Component;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Component
public class RegonNipValidationRule implements CreateEntrepreneurUserValidationRule
{
	@Override
	public void validate( final CreateUserDto aCreateUserDto )
	{
		final CreateEntrepreneursDataDto entrepreneursDataDto = aCreateUserDto.getEntrepreneursData();
		if ( !isRegonFilled( entrepreneursDataDto ) && !isNipFilled( entrepreneursDataDto ) )
		{
			throw new UserRegistrationException( "Regon or NIP should be filled" );
		}
		if ( isRegonFilled( entrepreneursDataDto ) && !hasRegonCorrectLength( entrepreneursDataDto ) )
		{
			throw new UserRegistrationException( "Regon should be 9 or 14 characters long" );
		}
		if ( isNipFilled( entrepreneursDataDto ) && !hasNipCorrectLength( entrepreneursDataDto ) )
		{
			throw new UserRegistrationException( "Nip should be 10 characters long" );
		}
	}

	private boolean isRegonFilled( final CreateEntrepreneursDataDto aCreateUserDto )
	{
		return !isBlank( aCreateUserDto.getRegon() );
	}

	private boolean hasRegonCorrectLength( final CreateEntrepreneursDataDto aCreateUserDto )
	{
		return aCreateUserDto.getRegon().length() == 9 || aCreateUserDto.getRegon().length() == 14;
	}

	private boolean isNipFilled( final CreateEntrepreneursDataDto aCreateUserDto )
	{
		return !isBlank( aCreateUserDto.getNip() );
	}

	private boolean hasNipCorrectLength( final CreateEntrepreneursDataDto aCreateUserDto )
	{
		return aCreateUserDto.getNip().length() == 10;
	}
}
