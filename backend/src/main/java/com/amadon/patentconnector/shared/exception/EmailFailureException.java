package com.amadon.patentconnector.shared.exception;

import jakarta.mail.MessagingException;
import lombok.Getter;

@Getter
public class EmailFailureException extends RuntimeException
{

	private String mailRecipient;
	private String mailSubject;

	public EmailFailureException( final String message )
	{
		super( message );
	}

	public EmailFailureException( final Exception messagingException, final String recipient, final String subject ) {
		super( messagingException );
		this.mailRecipient = recipient;
		this.mailSubject = subject;
	}
}
