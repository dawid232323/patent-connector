package com.amadon.patentconnector.configuration;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

@Slf4j
public class AppAuthenticationEntryPoint implements AuthenticationEntryPoint
{
	@Override
	public void commence( final HttpServletRequest request, final HttpServletResponse response, final AuthenticationException authException ) throws
			IOException, ServletException
	{
		log.error( "Unauthorized error for request {}. Caused by: {}", request.getRequestURI(),
				   authException.getMessage(), authException.getCause() );
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
	}
}
