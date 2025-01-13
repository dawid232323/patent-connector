package com.amadon.patentconnector.mail.service;

import com.amadon.patentconnector.mail.dto.EmailType;
import com.amadon.patentconnector.mail.dto.NewPatentNotificationEmailDto;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


@Slf4j
@Component
public class NewPatentNotificationMailSender implements MailSender< NewPatentNotificationEmailDto >
{

	private final JavaMailSender javaMailSender;
	private final TemplateEngine templateEngine;
	private final String sender;

	NewPatentNotificationMailSender( final JavaMailSender aJavaMailSender,
									 final TemplateEngine aTemplateEngine,
									 @Value( "${spring.mail.sender}" ) final String aMailSender )
	{
		javaMailSender = aJavaMailSender;
		templateEngine = aTemplateEngine;
		sender = aMailSender;
	}

	@Override
	public void send( final NewPatentNotificationEmailDto messageData )
	{
		final String recipient = messageData.getRecipient()
				.getEmail();
		send( messageData, recipient );
	}

	@Override
	public void send( final NewPatentNotificationEmailDto messageData, final String recipient )
	{
		log.info( "Sending new patent notification email to {}", recipient );

		final String messageContent = getMessageContent( messageData );
		final MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		final MimeMessageHelper messageHelper = new MimeMessageHelper( mimeMessage );
		try
		{

			messageHelper.setTo( recipient );
			messageHelper.setFrom( sender );
			messageHelper.setSubject( "Nowe patenty do sprawdzenia!" );
			messageHelper.setText( messageContent, true );

			javaMailSender.send( mimeMessage );

		} catch ( Exception e )
		{
			log.error( "Unable to send email to {}", recipient, e );
		}
	}

	@Override
	public boolean isApplicable( final EmailType emailType )
	{
		return emailType.equals( EmailType.NEW_PATENTS_NOTIFICATION );
	}

	private String getMessageContent( final NewPatentNotificationEmailDto messageData )
	{
		final Context context = new Context();
		context.setVariable( "patents", messageData.getPatents() );

		return templateEngine.process( EmailType.NEW_PATENTS_NOTIFICATION.getTemplatePath(), context );
	}
}
