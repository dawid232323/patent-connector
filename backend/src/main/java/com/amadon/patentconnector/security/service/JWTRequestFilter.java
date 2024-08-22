package com.amadon.patentconnector.security.service;

import com.amadon.patentconnector.shared.util.token.JWTService;
import com.amadon.patentconnector.user.entity.User;
import com.amadon.patentconnector.user.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class JWTRequestFilter extends OncePerRequestFilter
{
	private final JWTService jwtService;
	private final UserService userService;

	@Override
	protected void doFilterInternal( final HttpServletRequest aRequest, final HttpServletResponse aResponse,
									 final FilterChain aFilterChain ) throws
			ServletException, IOException
	{
		log.info( "Going to filter request {}", aRequest.getRequestURI() );
		final String authenticationHeader = aRequest.getHeader( HttpHeaders.AUTHORIZATION );

		if ( !jwtService.isAuthorizationTokenValid( authenticationHeader ) )
		{
			log.info( "Token for URI {} not validated correctly", aRequest.getRequestURI() );
			aFilterChain.doFilter( aRequest, aResponse );
			return;
		}

		if ( Objects.isNull( securityContext().getAuthentication() ) )
		{
			setSecurityContextAuthentication( aRequest );
		}

		aFilterChain.doFilter( aRequest, aResponse );
	}

	private void setSecurityContextAuthentication( final HttpServletRequest aRequest )
	{
		final String authenticationHeader = aRequest.getHeader( HttpHeaders.AUTHORIZATION );
		final String userEmail = jwtService.getUserEmailIfAuthTokenIsValid( authenticationHeader );
		if ( Objects.isNull( userEmail ) )
		{
			return;
		}
		final UserDetails userDetails = userService.loadUserByUsername( userEmail );
		final UsernamePasswordAuthenticationToken authenticationToken = UsernamePasswordAuthenticationToken
				.authenticated( userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities() );

		final WebAuthenticationDetails authenticationDetails =
				new WebAuthenticationDetailsSource().buildDetails( aRequest );

		authenticationToken.setDetails( authenticationDetails );
		securityContext().setAuthentication( authenticationToken );
	}

	private SecurityContext securityContext()
	{
		return SecurityContextHolder.getContext();
	}
}
