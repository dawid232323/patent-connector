package com.amadon.patentconnector.security.controller;

import com.amadon.patentconnector.security.service.exception.LoginException;
import com.amadon.patentconnector.shared.constants.DomainCode;
import com.amadon.patentconnector.shared.exception.AppErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

import static com.amadon.patentconnector.shared.constants.ReasonMessages.LOGIN_ERROR;

@Slf4j
@ControllerAdvice
public class SecurityControllerAdvice
{
	@ResponseBody
	@ResponseStatus( HttpStatus.FORBIDDEN )
	@ExceptionHandler( LoginException.class )
	public AppErrorResponse handleLoginException( final LoginException aE )
	{
		final String errorUuid = UUID.randomUUID().toString();
		log.error( "Login error with uuid {} occurred", errorUuid, aE );
		return AppErrorResponse.builder()
				.uuid( errorUuid )
				.status( HttpStatus.FORBIDDEN )
				.domainCode( DomainCode.LOGIN )
				.errorCause( LOGIN_ERROR )
				.originalExceptionMessage( aE.getMessage() )
				.build();
	}
}
