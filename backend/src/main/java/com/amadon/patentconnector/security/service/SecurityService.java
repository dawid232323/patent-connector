package com.amadon.patentconnector.security.service;

import com.amadon.patentconnector.security.service.dto.LoginDto;
import com.amadon.patentconnector.security.service.dto.TokenDto;
import com.amadon.patentconnector.security.service.exception.LoginException;
import com.amadon.patentconnector.shared.util.token.JWTService;
import com.amadon.patentconnector.user.entity.User;
import com.amadon.patentconnector.user.service.UserService;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class SecurityService
{
	private final JWTService jwtService;
	private final UserService userService;
	private final PasswordEncoder passwordEncoder;

	public TokenDto loginUser( final LoginDto aLoginDto )
	{
		final User userToLogIn = userService.tryToFindByEmail( aLoginDto.getEmail() )
				.orElseThrow( () -> new LoginException( "User with email " + aLoginDto.getEmail() + " does not exist" ) );

		validateIfUserCanLogIn( userToLogIn );
		validateIfPasswordMatches( userToLogIn, aLoginDto.getPassword() );

		return generateTokenPair( userToLogIn );
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
		if ( !passwordEncoder.matches( aUser.getPassword(), aPassedPassword ) )
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
