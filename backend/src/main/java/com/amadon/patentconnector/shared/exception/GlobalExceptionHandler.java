package com.amadon.patentconnector.shared.exception;

import com.amadon.patentconnector.shared.constants.DomainCode;
import com.amadon.patentconnector.shared.constants.ReasonMessages;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler
{

	@ResponseBody
	@ResponseStatus( HttpStatus.INTERNAL_SERVER_ERROR )
	@ExceptionHandler( Exception.class )
	public AppErrorResponse handleGlobalException( final Exception aE )
	{
		final String uuid = UUID.randomUUID()
				.toString();
		final String causeError = Objects.isNull( aE.getCause() ) ? null : aE.getCause().getMessage();
		log.error( "Unrecognised exception with uuid {}", uuid, aE );
		return AppErrorResponse.builder()
				.domainCode( DomainCode.UNRECOGNISED )
				.errorCause( ReasonMessages.UNEXPECTED_ERROR )
				.status( HttpStatus.INTERNAL_SERVER_ERROR )
				.message( aE.getMessage() )
				.uuid( uuid )
				.originalExceptionMessage( causeError )
				.errorTitle( "Unexpected error" )
				.build();
	}

	@ResponseBody
	@ResponseStatus( HttpStatus.NOT_FOUND )
	@ExceptionHandler( EntityNotFoundException.class )
	public AppErrorResponse handleEntityNotFound( final EntityNotFoundException e )
	{
		final String uuid = UUID.randomUUID()
				.toString();
		log.error( "Could not find entity with given id and uuid {}", uuid, e );
		return AppErrorResponse.builder()
				.domainCode( DomainCode.ENTITY )
				.errorCause( ReasonMessages.ENTITY_NOT_FOUND )
				.status( HttpStatus.NOT_FOUND )
				.message( e.getMessage() )
				.uuid( uuid )
				.build();
	}

	@Override
	protected ResponseEntity< Object > handleMethodArgumentNotValid( final MethodArgumentNotValidException ex,
																	 final HttpHeaders headers,
																	 final HttpStatusCode status,
																	 final WebRequest request )
	{
		final String uuid = UUID.randomUUID()
				.toString();
		log.error( "Validation error with uuid {}", uuid, ex );
		final Map< String, String > exceptionDetails = getValidationExceptionDetails( ex );
		final AppErrorResponse errorResponse = AppErrorResponse.builder()
				.domainCode( DomainCode.VALIDATION )
				.errorCause( ReasonMessages.INVALID_DATA )
				.status( HttpStatus.BAD_REQUEST )
				.errorTitle( "Validation error" )
				.uuid( uuid )
				.message( exceptionDetails.toString() )
				.build();
		return ResponseEntity.badRequest()
				.body( errorResponse );
	}

	private Map< String, String > getValidationExceptionDetails( final MethodArgumentNotValidException aE )
	{
		final Map< String, String > exceptionDetails = new HashMap<>();
		aE.getBindingResult()
				.getAllErrors()
				.forEach( error ->
						  {
							  final String fieldName = ( (FieldError) error ).getField();
							  final String message = error.getDefaultMessage();
							  exceptionDetails.put( fieldName, message );
						  } );
		return exceptionDetails;
	}
}
