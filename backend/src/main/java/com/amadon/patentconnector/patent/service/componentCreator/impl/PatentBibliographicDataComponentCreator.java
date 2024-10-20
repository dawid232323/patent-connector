package com.amadon.patentconnector.patent.service.componentCreator.impl;

import com.amadon.patentconnector.patent.entity.*;
import com.amadon.patentconnector.patent.service.componentCreator.PatentComponentCreator;
import com.amadon.patentconnector.patent.service.dto.create.CreateApplicationReferenceDto;
import com.amadon.patentconnector.patent.service.dto.create.CreateOtherPatentDocumentDto;
import com.amadon.patentconnector.patent.service.dto.create.CreatePatentAddressBookDto;
import com.amadon.patentconnector.patent.service.dto.create.CreatePatentBibliographicDatumDto;
import com.amadon.patentconnector.patent.service.mapper.PatentAddressBookMapper;
import com.amadon.patentconnector.patent.service.mapper.PatentBibliographicDatumMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class PatentBibliographicDataComponentCreator
		implements PatentComponentCreator< CreatePatentBibliographicDatumDto, PatentBibliographicDatum >
{
	private final PatentAddressBookMapper addressBookMapper;
	private final PatentBibliographicDatumMapper bibliographicDatumMapper;

	@Override
	public PatentBibliographicDatum resolvePatentComponent( final CreatePatentBibliographicDatumDto aCreateComponentDto )
	{
		final PatentBibliographicDatum bibliographicDatum = bibliographicDatumMapper.toEntityFromCreate( aCreateComponentDto );
		final ApplicationReference applicationReference = resolveApplicationReference( aCreateComponentDto.getApplicationReference() );

		bibliographicDatum.setApplicationReference( applicationReference );
		applicationReference.setBibliographicData( bibliographicDatum );
		bibliographicDatumMapper.linkClassificationsIpcr( bibliographicDatum );
		bibliographicDatumMapper.linkDatesOfPublicAvailability( bibliographicDatum );
		setAddressBooks( bibliographicDatum, aCreateComponentDto );

		return bibliographicDatum;
	}

	private void setAddressBooks( final PatentBibliographicDatum aBibliographicDatum,
								  final CreatePatentBibliographicDatumDto aCreatePatentBibliographicDatumDto )
	{
		final Set< PatentAddressBook > agents = aCreatePatentBibliographicDatumDto.getAgents()
				.stream()
				.map( aAgent -> resolveAddressBook( aAgent, AddressBookTypeEnum.AGENT ) )
				.collect( Collectors.toSet() );
		final Set< PatentAddressBook > applicants = aCreatePatentBibliographicDatumDto.getApplicants()
				.stream()
				.map( aAgent -> resolveAddressBook( aAgent, AddressBookTypeEnum.APPLICANT ) )
				.collect( Collectors.toSet() );
		final Set< PatentAddressBook > assignees = aCreatePatentBibliographicDatumDto.getAssignees()
				.stream()
				.map( aAgent -> resolveAddressBook( aAgent, AddressBookTypeEnum.ASSIGNEE ) )
				.collect( Collectors.toSet() );
		final Set< PatentAddressBook > inventors = aCreatePatentBibliographicDatumDto.getInventors()
				.stream()
				.map( aAgent -> resolveAddressBook( aAgent, AddressBookTypeEnum.INVENTOR ) )
				.collect( Collectors.toSet() );

		aBibliographicDatum.getAgents().addAll( agents );
		agents.forEach( agent -> agent.addBibliographicDataMember( aBibliographicDatum, AddressBookTypeEnum.AGENT ) );

		aBibliographicDatum.getApplicants().addAll( applicants );
		applicants.forEach( applicant -> applicant.addBibliographicDataMember( aBibliographicDatum, AddressBookTypeEnum.APPLICANT ) );

		aBibliographicDatum.getAssignees().addAll( assignees );
		assignees.forEach( assignee -> assignee.addBibliographicDataMember( aBibliographicDatum, AddressBookTypeEnum.ASSIGNEE ) );

		aBibliographicDatum.getInventors().addAll( inventors );
		inventors.forEach( inventor -> inventor.addBibliographicDataMember( aBibliographicDatum, AddressBookTypeEnum.INVENTOR ) );
	}

	private PatentAddressBook resolveAddressBook( final CreatePatentAddressBookDto aAddressBookDto,
												  final AddressBookTypeEnum aTypeEnum )
	{
		final PatentAddressBook addressBook = addressBookMapper.toEntityFromCreateDto( aAddressBookDto );
		addressBook.setAddressBookType( aTypeEnum );
		return addressBook;
	}

	private ApplicationReference resolveApplicationReference( final CreateApplicationReferenceDto aReferenceDto )
	{
		final Set< OtherPatentDocument > patentDocuments = aReferenceDto.getOtherPatentDocuments()
				.stream()
				.map( this::resolveOtherPatentDocument )
				.collect( Collectors.toSet() );
		final ApplicationReference applicationReference = ApplicationReference.builder()
				.documentCountryId( aReferenceDto.getDocumentCountryId() )
				.documentNumber( aReferenceDto.getDocumentNumber() )
				.documentKind( aReferenceDto.getDocumentKind() )
				.documentDate( aReferenceDto.getDocumentDate() )
				.otherPatentDocuments( patentDocuments )
				.build();
		patentDocuments.forEach( document -> document.setApplicationReference( applicationReference ) );
		return applicationReference;
	}

	private OtherPatentDocument resolveOtherPatentDocument( final CreateOtherPatentDocumentDto aDocumentDto )
	{
		return OtherPatentDocument.builder()
				.documentCode( aDocumentDto.getDocumentCode() )
				.documentUri( aDocumentDto.getDocumentUri() )
				.build();
	}
}
