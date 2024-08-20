package com.amadon.patentconnector.user.service.registrationPerformer.impl;

import com.amadon.patentconnector.user.entity.User;
import com.amadon.patentconnector.user.entity.UserRole;
import com.amadon.patentconnector.user.service.dto.CreateUserDto;
import com.amadon.patentconnector.user.service.mapper.UserMapper;
import com.amadon.patentconnector.user.service.registrationPerformer.RegistrationPerformer;
import com.amadon.patentconnector.user.service.registrationPerformer.RegistrationType;
import com.amadon.patentconnector.user.service.validator.EntrepreneurValidationRuleEngine;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class EntrepreneurRegistrationPerformer implements RegistrationPerformer
{
	private final EntrepreneurValidationRuleEngine validator;
	private final UserMapper userMapper;

	@Override
	public User resolveUserToRegister( final CreateUserDto aCreateUserDto )
	{
		validator.validate( aCreateUserDto );
		log.info( "User {} passed validation", aCreateUserDto.getEmail() );
		final User createdUser = userMapper.fromCreateToEntity( aCreateUserDto );
		createdUser.setAuthorities( UserRole.ENTREPRENEUR );
		createdUser.getEntrepreneursData().setUser( createdUser );
		return createdUser;
	}

	@Override
	public boolean isApplicable( final RegistrationType aRegistrationType )
	{
		return aRegistrationType.equals( RegistrationType.BASIC_ENTREPRENEUR );
	}
}
