package com.amadon.patentconnector.configuration;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

@Slf4j
public class AppAccessDeniedHandler implements AccessDeniedHandler
{
	@Override
	public void handle( final HttpServletRequest request, final HttpServletResponse response,
						final AccessDeniedException accessDeniedException ) throws
			IOException, ServletException
	{
		log.error( "Forbidden error for request {}. Caused by: {}", request.getRequestURI(),
				   accessDeniedException.getMessage(), accessDeniedException.getCause() );
		response.sendError( HttpServletResponse.SC_FORBIDDEN, "Forbidden" );
	}
}
