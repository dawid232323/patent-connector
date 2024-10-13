package com.amadon.patentconnector.security.service;

import com.amadon.patentconnector.security.service.dto.LoginDto;
import com.amadon.patentconnector.security.service.dto.TokenDto;
import com.amadon.patentconnector.security.service.exception.LoginException;
import com.amadon.patentconnector.shared.util.token.JWTService;
import com.amadon.patentconnector.user.entity.User;
import com.amadon.patentconnector.user.service.repository.UserRepository;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class SecurityService
{
	private final JWTService jwtService;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final String adminKey;

	public SecurityService( final JWTService aJwtService,
							final UserRepository aUserRepository,
							final PasswordEncoder aPasswordEncoder,
							@Value( "${spring.application.admin-key}" ) final String aAdminKey )
	{
		jwtService = aJwtService;
		userRepository = aUserRepository;
		passwordEncoder = aPasswordEncoder;
		adminKey = aAdminKey;
	}

	public TokenDto loginUser( final LoginDto aLoginDto )
	{
		final User userToLogIn = userRepository.findByEmail( aLoginDto.getEmail() )
				.orElseThrow( () -> new LoginException( "User with email " + aLoginDto.getEmail() + " does not exist" ) );

		validateIfUserCanLogIn( userToLogIn );
		validateIfPasswordMatches( userToLogIn, aLoginDto.getPassword() );

		return generateTokenPair( userToLogIn );
	}

	public TokenDto refreshToken( final String aRefreshToken )
	{
		final String userEmail = Optional.ofNullable( jwtService.getUserEmailFromTokenIfIsValid( aRefreshToken ) )
				.orElseThrow( () -> new AccessDeniedException( "Token is invalid or expired" ) );
		final User user = userRepository.findByEmail( userEmail )
				.orElseThrow( () -> new LoginException( "User with email " + userEmail + " does not exist" ) );
		return generateTokenPair( user );
	}

	public boolean isUserAuthenticated()
	{
		final Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		if ( Objects.isNull( authentication ) )
		{
			return false;
		}
		return authentication.isAuthenticated();
	}

	@Nullable
	public String getAuthenticatedUserEmail()
	{
		if ( !isUserAuthenticated() )
		{
			return null;
		}
		return String.valueOf( SecurityContextHolder.getContext()
									   .getAuthentication()
									   .getPrincipal() );
	}

	public void validateAdminKey( final String aAdminKey )
	{
		if ( !aAdminKey.equals( adminKey ) )
		{
			throw new AccessDeniedException( "Admin key is not valid" );
		}
	}

	private void validateIfUserCanLogIn( final User aUser )
	{
		if ( !aUser.getIsActive() )
		{
			log.warn( "Found inactive user {} trying to log in", aUser.getEmail() );
			throw new LoginException( String.format( "User is %s is inactive. Please contact system administrator " +
															 "for" +
															 " " +
															 "more details", aUser.getEmail() ) );
		}
	}

	private void validateIfPasswordMatches( final User aUser, final String aPassedPassword )
	{
		if ( !passwordEncoder.matches( aPassedPassword, aUser.getPassword() ) )
		{
			log.warn( "User {} passed wrong password. Comparing {} against {}", aUser.getEmail(), aUser.getPassword(),
					  aPassedPassword );
			throw new LoginException( "Email or password is invalid, please try again" );
		}
	}

	private TokenDto generateTokenPair( final User aUser )
	{
		final String token = jwtService.generateTokenForUserAuthentication( aUser, false );
		final String refreshToken = jwtService.generateTokenForUserAuthentication( aUser, true );
		log.info( "Token pair for user {} generated successfully", aUser.getEmail() );
		return TokenDto.builder()
				.token( token )
				.refreshToken( refreshToken )
				.build();
	}
}
