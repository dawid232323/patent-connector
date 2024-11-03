package com.amadon.patentconnector.patent.service.mapper;

import com.amadon.patentconnector.businessBranch.entity.BusinessBranch;
import com.amadon.patentconnector.patent.entity.Patent;
import com.amadon.patentconnector.patent.entity.PatentAnalysisDatum;
import com.amadon.patentconnector.patent.entity.PatentBibliographicDatum;
import com.amadon.patentconnector.patent.service.dto.PatentSearchResultDto;
import com.amadon.patentconnector.patent.service.dto.create.CreatePatentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
abstract class PatentMapperDecorator implements PatentMapper
{

	@Autowired
	@Qualifier( "delegate" )
	private PatentMapper mapperDelegate;

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
}
