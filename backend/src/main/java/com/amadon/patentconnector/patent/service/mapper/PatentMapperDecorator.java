package com.amadon.patentconnector.patent.service.mapper;

import com.amadon.patentconnector.businessBranch.entity.BusinessBranch;
import com.amadon.patentconnector.patent.entity.Patent;
import com.amadon.patentconnector.patent.entity.PatentAnalysisDatum;
import com.amadon.patentconnector.patent.entity.PatentBibliographicDatum;
import com.amadon.patentconnector.patent.entity.PatentUsageDescription;
import com.amadon.patentconnector.patent.service.dto.PatentDto;
import com.amadon.patentconnector.patent.service.dto.PatentSearchResultDto;
import com.amadon.patentconnector.patent.service.dto.create.CreatePatentDto;
import com.amadon.patentconnector.patent.service.dto.partials.PatentCitationDto;
import com.amadon.patentconnector.patent.service.dto.partials.PatentDocumentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
abstract class PatentMapperDecorator implements PatentMapper
{

	@Autowired
	@Qualifier( "delegate" )
	private PatentMapper mapperDelegate;

	@Autowired
	private PatentAddressBookMapper addressMapper;

	@Override
	public Patent fromCreateDto( final CreatePatentDto aCreatePatentDto )
	{
		return mapperDelegate.fromCreateDto( aCreatePatentDto );
	}

	@Override
	public PatentSearchResultDto toPatentSearchResultDto( final Patent patent )
	{
		final PatentSearchResultDto resultDto = mapperDelegate.toPatentSearchResultDto( patent );
		resultDto.setName( getName( patent ) );
		resultDto.setBusinessBranches( getBusinessBranchesNames( patent ) );
		return resultDto;
	}

	@Override
	public PatentDto fromEntityToDto( final Patent patent )
	{
		final PatentDto resultDto = mapperDelegate.fromEntityToDto( patent );
		resultDto.setBusinessBranches( getBusinessBranchesNames( patent ) );
		resultDto.setTitle( getName( patent ) );
		resultDto.setDocuments( getPatentDocuments( patent ) );
		resultDto.setCitations( getPatentCitations( patent ) );
		resultDto.setPatentNumber( patent.getExtidappli() );

		setParties( patent, resultDto );
		setUsageDescriptions( patent, resultDto );

		return resultDto;
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
		return null;
	}

	private List< String > getBusinessBranchesNames( final Patent patent )
	{
		final PatentAnalysisDatum analysisDatum = patent.getPatentAnalysisData();
		return analysisDatum.getBusinessBranches()
				.stream()
				.map( BusinessBranch::getDisplayName )
				.toList();
	}

	private List< PatentDocumentDto > getPatentDocuments( final Patent aPatent )
	{
		final PatentBibliographicDatum bibliographicData = aPatent.getBibliographicData();
		return bibliographicData.getApplicationReference()
				.getOtherPatentDocuments()
				.stream()
				.map( document -> PatentDocumentDto.builder()
						.documentCode( document.getDocumentCode() )
						.documentUri( document.getDocumentUri() )
						.build() )
				.toList();
	}

	private List< PatentCitationDto > getPatentCitations( final Patent aPatent )
	{
		return aPatent.getSearchReportData()
				.getPatentCitations()
				.stream()
				.map( citation -> PatentCitationDto.builder()
						.citationText( citation.getCitationText() )
						.documentName( citation.getDocumentName() )
						.documentNumber( citation.getDocumentNumber() )
						.publicationDate( citation.getDocumentPublicationDate() )
						.build() )
				.toList();
	}

	private void setParties( final Patent aPatent, final PatentDto patentDto )
	{
		final PatentBibliographicDatum bibliographicData = aPatent.getBibliographicData();

		patentDto.setAgents( bibliographicData.getAgents()
									 .stream()
									 .map( addressMapper::toDtoFromEntity )
									 .collect( Collectors.toList() ) );
		patentDto.setApplicants( bibliographicData.getApplicants()
										 .stream()
										 .map( addressMapper::toDtoFromEntity )
										 .collect( Collectors.toList() ) );
		patentDto.setAssignees( bibliographicData.getAssignees()
										.stream()
										.map( addressMapper::toDtoFromEntity )
										.collect( Collectors.toList() ) );
		patentDto.setInventors( bibliographicData.getInventors()
										.stream()
										.map( addressMapper::toDtoFromEntity )
										.collect( Collectors.toList() ) );
	}

	private void setUsageDescriptions( final Patent aPatent, final PatentDto patentDto )
	{
		final Set< PatentUsageDescription > patentUsageDescriptions = aPatent.getPatentAnalysisData()
				.getPatentUsageDescriptions();
		final Map< String, List< String > > usageDescriptions = patentUsageDescriptions.stream()
				.collect( Collectors.toMap( usage -> usage.getBusinessBranch()
						.getDisplayName(), PatentUsageDescription::getDescriptions ) );
		patentDto.setUsageDescriptions( usageDescriptions );
	}
}
