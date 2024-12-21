package com.amadon.patentconnector.mail.service;

import com.amadon.patentconnector.mail.dto.EmailType;
import com.amadon.patentconnector.mail.dto.UserRegistrationEmailDto;
import com.amadon.patentconnector.shared.exception.EmailFailureException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


@Slf4j
@Service
public class RegistrationMailSender implements MailSender< UserRegistrationEmailDto >
{
	private final JavaMailSender javaMailSender;
	private final TemplateEngine templateEngine;
	private final String sender;
	private final String applicationHost;

	RegistrationMailSender( final JavaMailSender aJavaMailSender, final TemplateEngine aTemplateEngine,
							@Value( "${spring.mail.sender}" ) final String aMailSender,
							@Value( "${spring.application.host}" ) final String aHost )
	{
		javaMailSender = aJavaMailSender;
		templateEngine = aTemplateEngine;
		sender = aMailSender;
		applicationHost = aHost;
	}

	@Override
	public void send( final UserRegistrationEmailDto messageData )
	{
		final String userEmail = messageData.getUser()
				.getEmail();
		send( messageData, userEmail );
	}

	@Override
	public void send( final UserRegistrationEmailDto messageData, final String recipient )
	{
		final String content = getMessageContent( messageData );

		final MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		try
		{
		final MimeMessageHelper helper = new MimeMessageHelper( mimeMessage, true );

		helper.setTo( recipient );
		helper.setSubject( getSubject() );
		helper.setText( content, true );
		helper.setFrom( sender );

		log.info( "Sending registration email to {}", recipient );
		javaMailSender.send( mimeMessage );

		} catch ( Exception e ) {
			throw new EmailFailureException( e, recipient, getSubject() );
		}
	}

	@Override
	public boolean isApplicable( final EmailType emailType )
	{
		return EmailType.REGISTRATION.equals( emailType );
	}

	private String getMessageContent( final UserRegistrationEmailDto messageData )
	{
		final Context context = new Context();
		context.setVariable( "registrationUrl", getRegistrationUrl( messageData.getToken() ) );
		context.setVariable( "name", messageData.getUser()
				.getName()
				.concat( " " )
				.concat( messageData.getUser()
								 .getLastName() ) );
		log.info( "Rendering registration email content for {}", context.getVariable( "name" ) );
		return templateEngine.process( EmailType.REGISTRATION.getTemplatePath(), context );
	}

	private String getSubject() {
		return "Rejestracja w systemie PatentConnector";
	}

	private String getRegistrationUrl( final String aToken )
	{
		return applicationHost.concat( "/activate-account?t=" ).concat( aToken );
	}
}
