package com.amadon.patentconnector.user.controller;

import com.amadon.patentconnector.shared.constants.DomainCode;
import com.amadon.patentconnector.shared.constants.ReasonMessages;
import com.amadon.patentconnector.shared.exception.AppErrorResponse;
import com.amadon.patentconnector.user.service.exception.RoleMismatchException;
import com.amadon.patentconnector.user.service.exception.UserNotFoundException;
import com.amadon.patentconnector.user.service.exception.UserRegistrationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@Slf4j
@ControllerAdvice
public class AdviceUserController
{
	@ResponseBody
	@ResponseStatus( HttpStatus.BAD_REQUEST )
	@ExceptionHandler( UserRegistrationException.class )
	public AppErrorResponse handleUserRegistrationException( final UserRegistrationException aE )
	{
		final String errorUUID = UUID.randomUUID().toString();
		log.error( "Error with id {} occurred during the user registration process. Original exception is:", errorUUID, aE );
		return AppErrorResponse.builder()
				.status( HttpStatus.BAD_REQUEST )
				.message( aE.getMessage() )
				.domainCode( DomainCode.REGISTRATION )
				.errorCause( ReasonMessages.INVALID_DATA )
				.uuid( errorUUID )
				.originalExceptionMessage( aE.getMessage() )
				.build();
	}

	@ResponseBody
	@ResponseStatus( HttpStatus.BAD_REQUEST )
	@ExceptionHandler( UsernameNotFoundException.class )
	public AppErrorResponse	handleUsernameNotFoundException( final UsernameNotFoundException aE )
	{
		final String errorUUID = UUID.randomUUID().toString();
		log.error( "UsernameNotFoundException uid: {} Original exception is:", errorUUID, aE );
		return AppErrorResponse.builder()
				.status( HttpStatus.BAD_REQUEST )
				.message( aE.getMessage() )
				.domainCode( DomainCode.USER )
				.errorCause( ReasonMessages.USER_DOES_NOT_EXIST )
				.uuid( errorUUID )
				.originalExceptionMessage( aE.getMessage() )
				.build();
	}

	@ResponseBody
	@ResponseStatus( HttpStatus.BAD_REQUEST )
	@ExceptionHandler( UserNotFoundException.class )
	public AppErrorResponse handleUserNotFoundException( final UserNotFoundException aE )
	{
		final String errorUUID = UUID.randomUUID().toString();
		log.error( "UserNotFoundException uid: {} Original exception is:", errorUUID, aE );
		return AppErrorResponse.builder()
				.status( HttpStatus.BAD_REQUEST )
				.message( aE.getMessage() )
				.domainCode( DomainCode.USER )
				.errorCause( ReasonMessages.USER_DOES_NOT_EXIST )
				.uuid( errorUUID )
				.originalExceptionMessage( aE.getMessage() )
				.build();
	}

	@ResponseBody
	@ResponseStatus( HttpStatus.BAD_REQUEST )
	@ExceptionHandler( RoleMismatchException.class )
	public AppErrorResponse handleRoleMismatchException( final RoleMismatchException aE )
	{
		final String errorUUID = UUID.randomUUID().toString();
		log.error( "RoleMismatchException uid: {} Original exception is:", errorUUID, aE );
		return AppErrorResponse.builder()
				.status( HttpStatus.BAD_REQUEST )
				.message( aE.getMessage() )
				.domainCode( DomainCode.USER )
				.errorCause( ReasonMessages.ROLE_MISMATCH )
				.uuid( errorUUID )
				.originalExceptionMessage( aE.getMessage() )
				.build();
	}
}
