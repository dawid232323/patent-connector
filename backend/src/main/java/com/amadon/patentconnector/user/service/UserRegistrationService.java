package com.amadon.patentconnector.user.service;

import com.amadon.patentconnector.shared.util.hash.HashGenerator;
import com.amadon.patentconnector.shared.util.token.JWTGenerator;
import com.amadon.patentconnector.user.entity.User;
import com.amadon.patentconnector.user.service.dto.CreateUserDto;
import com.amadon.patentconnector.user.service.registrationPerformer.RegistrationPerformer;
import com.amadon.patentconnector.user.service.registrationPerformer.RegistrationType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserRegistrationService
{
	private final UserPersistenceService persistenceService;
	private final List< RegistrationPerformer > registrationPerformers;
	private final JWTGenerator jwtGenerator;
	private final HashGenerator hashGenerator;

	@Transactional
	public User registerUser( final CreateUserDto aCreateUserDto, final RegistrationType aRegistrationType )
	{
		log.info( "Started registration process for user {}", aCreateUserDto.getEmail() );
		final RegistrationPerformer registrationPerformer = getRegistrationPerformer( aRegistrationType );
		final User userToRegister = registrationPerformer.resolveUserToRegister( aCreateUserDto );

		setCommonUserInitialParameters( userToRegister );
		persistenceService.save( userToRegister );

		createAndSendMessageWithToken( userToRegister );
		return userToRegister;
	}

	private RegistrationPerformer getRegistrationPerformer( final RegistrationType aRegistrationType )
	{
		return registrationPerformers.stream()
				.filter( performer -> performer.isApplicable( aRegistrationType ) )
				.findFirst()
				.orElseThrow();
	}

	private void setCommonUserInitialParameters( final User aUser )
	{
		aUser.setPassword( "" );
		aUser.setIsActive( false );
		aUser.setSecretKey( generateUserSecret( aUser.getEmail() ) );
		log.info( "Generated secret key for user {}", aUser.getEmail() );
	}

	// TODO when email service is introduced mailing should be implemented
	private void createAndSendMessageWithToken( final User aRegisteredUser )
	{
		final String userRegistrationToken = jwtGenerator.generateTokenForUserRegistration( aRegisteredUser.getEmail()
				, aRegisteredUser.getSecretKey() );
		log.warn( "[TEMP] until email messaging is resolved, user registration token is {}", userRegistrationToken );
	}

	private String generateUserSecret( final String aUserEmail )
	{
		return hashGenerator.generateHashFromString( aUserEmail );
	}
}
