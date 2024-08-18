package com.amadon.patentconnector.user.service.validator.impl;

import com.amadon.patentconnector.user.entity.User;
import com.amadon.patentconnector.user.service.UserService;
import com.amadon.patentconnector.user.service.dto.CreateUserDto;
import com.amadon.patentconnector.user.service.exception.UserRegistrationException;
import com.amadon.patentconnector.user.service.validator.CommonUserRegistrationValidatorRule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UniqueUserValidationRule implements CommonUserRegistrationValidatorRule
{
	private final UserService userService;

	@Override
	public void validate( final CreateUserDto aCreateUserDto )
	{
		final Optional< User > existingUser = userService.tryToFindByEmail( aCreateUserDto.getEmail() );
		if ( existingUser.isPresent() )
		{
			throw new UserRegistrationException( String.format( "User with email %s already exists",
					aCreateUserDto.getEmail() ) );
		}
	}
}
