package com.amadon.patentconnector.user.service;

import com.amadon.patentconnector.shared.util.hash.HashGenerator;
import com.amadon.patentconnector.shared.util.token.JWTService;
import com.amadon.patentconnector.user.entity.User;
import com.amadon.patentconnector.user.service.dto.CreateUserDto;
import com.amadon.patentconnector.user.service.dto.SetPasswordDto;
import com.amadon.patentconnector.user.service.registrationPerformer.RegistrationPerformer;
import com.amadon.patentconnector.user.service.registrationPerformer.RegistrationType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
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
	private final JWTService jwtService;
	private final HashGenerator hashGenerator;
	private final UserService userService;
	private final PasswordEncoder passwordEncoder;
	private final UserPersistenceService userPersistenceService;

	@Transactional
	public void registerUser( final CreateUserDto aCreateUserDto, final RegistrationType aRegistrationType )
	{
		log.info( "Started registration process for user {}", aCreateUserDto.getEmail() );
		final RegistrationPerformer registrationPerformer = getRegistrationPerformer( aRegistrationType );
		final User userToRegister = registrationPerformer.resolveUserToRegister( aCreateUserDto );

		setCommonUserInitialParameters( userToRegister );
		persistenceService.save( userToRegister );

		createAndSendMessageWithToken( userToRegister );
	}

	@Transactional
	public void setUserInitialPassword( final SetPasswordDto aInitialPassword )
	{
		final String userEmail = jwtService.getRegisteredUserEmailFromToken( aInitialPassword.getValidationToken() );
		final User user = userService.tryToFindByEmail( userEmail )
									 .orElseThrow( () -> new UsernameNotFoundException( "Could not find user with " +
																								"email " + userEmail ) );
		final String encryptedPassword = passwordEncoder.encode( aInitialPassword.getPassword() );

		user.setIsActive( true );
		user.setPassword( encryptedPassword );
		log.info( "Successfully encoded password for user {}", userEmail );

		userPersistenceService.save( user );
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
		final String userRegistrationToken = jwtService.generateTokenForUserRegistration( aRegisteredUser.getEmail()
				, aRegisteredUser.getSecretKey() );
		log.warn( "[TEMP] until email messaging is resolved, user registration token is {}", userRegistrationToken );
	}

	private String generateUserSecret( final String aUserEmail )
	{
		return hashGenerator.generateHashFromString( aUserEmail );
	}
}