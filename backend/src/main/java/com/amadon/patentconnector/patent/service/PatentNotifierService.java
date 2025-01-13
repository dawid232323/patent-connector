package com.amadon.patentconnector.patent.service;

import com.amadon.patentconnector.businessBranch.entity.BusinessBranch;
import com.amadon.patentconnector.mail.dto.NewPatentNotificationEmailDto;
import com.amadon.patentconnector.mail.service.MailSender;
import com.amadon.patentconnector.patent.entity.Patent;
import com.amadon.patentconnector.patent.entity.PatentBibliographicDatum;
import com.amadon.patentconnector.user.entity.User;
import com.amadon.patentconnector.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
class PatentNotifierService
{
	private final UserService userService;
	private final MailSender< NewPatentNotificationEmailDto > mailSender;
	private final String applicationHost;

	public PatentNotifierService( final UserService aUserService,
								  final MailSender< NewPatentNotificationEmailDto > aMailSender,
								  @Value( "${spring.application.host}" ) final String aHost )
	{
		userService = aUserService;
		mailSender = aMailSender;
		applicationHost = aHost;
	}

	public void notifyAboutNewPatents( final List< Patent > patents )
	{
		final Map< String, List< Patent > > patentMap = getPatentBusinessBranches( patents );
		final Set< String > sectionCodes = patentMap.keySet();
		final List< User > availableUsers = userService.getUsersToNotifyAboutNewPatents( sectionCodes );
		final Map< Long, Set< Patent > > userPatents = getUserPatents( availableUsers, patentMap );
		final List< NewPatentNotificationEmailDto > emailDtos = getEmailData( userPatents, availableUsers );
		emailDtos.forEach( mailSender::send );
	}

	private Map< String, List< Patent > > getPatentBusinessBranches( final List< Patent > patents )
	{
		return patents.stream()
				.flatMap( patent -> patent.getPatentAnalysisData()
						.getBusinessBranches()
						.stream()
						.map( branch -> Map.entry( branch.getSection()
														   .trim(), patent ) ) )
				.collect( Collectors.groupingBy( Map.Entry::getKey, Collectors.mapping( Map.Entry::getValue,
																						Collectors.toList() ) ) );
	}

	private Map< Long, Set< Patent > > getUserPatents( List< User > availableUsers,
													   Map< String, List< Patent > > patentsBySectionBranches )
	{
		return availableUsers.stream()
				.flatMap( user -> user.getEntrepreneursData()
								  .getInterestedBusinessBranches()
								  .stream()
								  .map( branch -> Map.entry( user.getId(),
															 patentsBySectionBranches.get( branch.getSection()
																								   .trim() ) ) )
						)
				.collect( Collectors.groupingBy(
						Map.Entry::getKey,
						Collectors.flatMapping( entry -> entry.getValue()
								.stream(), Collectors.toSet() )
											   ) );
	}

	private List< NewPatentNotificationEmailDto > getEmailData( final Map< Long, Set< Patent > > aUserPatents,
																final List< User > availableUsers )
	{
		return availableUsers.stream()
				.map( user ->
					  {
						  final List< Patent > userPatents = aUserPatents.get( user.getId() )
								  .stream()
								  .toList();
						  return NewPatentNotificationEmailDto.builder()
								  .recipient( user )
								  .patents( userPatents.stream()
													.map( this::getPatentNotificationDetails )
													.toList() )
								  .build();
					  } )
				.toList();
	}

	private NewPatentNotificationEmailDto.PatentNotificationDetails getPatentNotificationDetails( final Patent aUserPatent )
	{
		return NewPatentNotificationEmailDto.PatentNotificationDetails.builder()
				.name( getName( aUserPatent ) )
				.businessBranches( aUserPatent.getPatentAnalysisData()
										   .getBusinessBranches()
										   .stream()
										   .map( BusinessBranch::getDisplayName )
										   .toList() )
				.link( getLink( aUserPatent ) )
				.build();
	}

	private String getName( final Patent patent )
	{
		if ( Objects.nonNull( patent.getTitle() ) )
		{
			return patent.getTitle();
		}
		final PatentBibliographicDatum bibliographicDatum = patent.getBibliographicData();
		if ( Objects.nonNull( bibliographicDatum.getInventionTitle() ) )
		{
			return bibliographicDatum.getInventionTitle();
		}
		return "Nieznany";
	}

	private String getLink( final Patent aPatent )
	{
		return String.format( "%s/patents/%d", applicationHost, aPatent.getId() );
	}
}
