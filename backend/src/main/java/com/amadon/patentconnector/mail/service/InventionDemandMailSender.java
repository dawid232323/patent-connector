package com.amadon.patentconnector.mail.service;

import com.amadon.patentconnector.businessBranch.entity.BusinessBranch;
import com.amadon.patentconnector.inventionDemand.entity.InventionDemand;
import com.amadon.patentconnector.mail.dto.EmailType;
import com.amadon.patentconnector.researchInstitution.entity.ResearchInstitution;
import com.amadon.patentconnector.shared.exception.EmailFailureException;
import com.amadon.patentconnector.user.features.entrepreneurData.entity.EntrepreneursData;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Slf4j
@Service
public class InventionDemandMailSender implements MailSender< InventionDemand >
{
	private final JavaMailSender javaMailSender;
	private final TemplateEngine templateEngine;
	private final String sender;

	InventionDemandMailSender( final JavaMailSender aJavaMailSender,
							   final TemplateEngine aTemplateEngine,
							   @Value( "${spring.mail.sender}" ) final String aMailSender )
	{
		javaMailSender = aJavaMailSender;
		templateEngine = aTemplateEngine;
		sender = aMailSender;
	}

	@Override
	public void send( final InventionDemand messageData )
	{
		final String recipient = messageData.getRecipient()
				.getEmail();
		send( messageData, recipient );
	}

	@Override
	public void send( final InventionDemand messageData, final String recipient )
	{
		final String messageContent = getMessageContent( messageData );
		final MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		final MimeMessageHelper messageHelper = new MimeMessageHelper( mimeMessage );
		log.info( "Successfully parsed html content for invention demand message to {}", recipient );

		try
		{
			messageHelper.setTo( recipient );
			messageHelper.setSubject( getSubject() );
			messageHelper.setCc( messageData.getIssuer()
										 .getEmail() );
			messageHelper.setFrom( sender );
			messageHelper.setText( messageContent, true );

			log.info( "Sending invention demand email to {}", recipient );
			javaMailSender.send( mimeMessage );

		} catch ( Exception e )
		{
			throw new EmailFailureException( e, recipient, getSubject() );
		}
	}

	@Override
	public boolean isApplicable( final EmailType emailType )
	{
		return EmailType.INVENTION_DEMAND.equals( emailType );
	}

	private String getMessageContent( final InventionDemand messageData )
	{
		final Context context = new Context();
		setIssuerData( messageData, context );
		setDemandData( messageData, context );

		return templateEngine.process( EmailType.INVENTION_DEMAND.getTemplatePath(), context );
	}

	private void setIssuerData( final InventionDemand messageData, final Context aContext )
	{
		final EntrepreneursData entrepreneursData = messageData.getIssuer()
				.getEntrepreneursData();
		final List< String > businessBranches =
				getBusinessBranches( entrepreneursData.getInterestedBusinessBranches() );
		final String name = String.format( "%s %s", messageData.getIssuer()
				.getName(), messageData.getIssuer()
												   .getLastName() );

		aContext.setVariable( "name", name );
		aContext.setVariable( "companyName", entrepreneursData.getCompanyName() );
		aContext.setVariable( "hasRegon", !Objects.isNull( entrepreneursData.getRegon() ) );
		aContext.setVariable( "regon", entrepreneursData.getRegon() );
		aContext.setVariable( "nip", entrepreneursData.getNip() );
		aContext.setVariable( "enterpriseBusinessBranches", businessBranches );
	}

	private void setDemandData( final InventionDemand aInventionDemand, final Context aContext )
	{
		final List< String > businessBranches = getBusinessBranches( aInventionDemand.getBusinessBranches() );

		aContext.setVariable( "inventionBusinessBranches", businessBranches );
		aContext.setVariable( "inventionContent", aInventionDemand.getDemandContent() );
	}

	private List< String > getBusinessBranches( final Set< BusinessBranch > aBusinessBranches )
	{
		return aBusinessBranches.stream()
				.map( this::resolveBusinessBranch )
				.toList();
	}

	private String resolveBusinessBranch( final BusinessBranch aBusinessBranch )
	{
		if ( Objects.isNull( aBusinessBranch.getCode() ) )
		{
			return aBusinessBranch.getDisplayName();
		}
		return  String.format( "%s (%s)", aBusinessBranch.getDisplayName(), aBusinessBranch.getCode() );
	}

	private String getSubject()
	{
		return "Pojawiło się nowe zapotrzebowanie na stworzenie patentu";
	}
}
