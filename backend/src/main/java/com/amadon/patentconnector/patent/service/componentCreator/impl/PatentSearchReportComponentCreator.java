package com.amadon.patentconnector.patent.service.componentCreator.impl;

import com.amadon.patentconnector.patent.entity.PatentCitation;
import com.amadon.patentconnector.patent.entity.PatentSearchReportDatum;
import com.amadon.patentconnector.patent.service.componentCreator.PatentComponentCreator;
import com.amadon.patentconnector.patent.service.dto.create.CreatePatentCitationDto;
import com.amadon.patentconnector.patent.service.dto.create.CretaePatentSearchReportDatumDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class PatentSearchReportComponentCreator
		implements PatentComponentCreator< CretaePatentSearchReportDatumDto, PatentSearchReportDatum >
{
	@Override
	public PatentSearchReportDatum resolvePatentComponent( final CretaePatentSearchReportDatumDto aCreateComponentDto )
	{
		final Set< PatentCitation > citations = aCreateComponentDto.getPatentCitations()
				.stream()
				.map( this::resolveCitation )
				.collect( Collectors.toSet() );
		return PatentSearchReportDatum.builder()
				.patentCitations( citations )
				.build();
	}

	private PatentCitation resolveCitation( final CreatePatentCitationDto aCitationDto )
	{
		return PatentCitation.builder()
				.documentCountry( aCitationDto.getDocumentCountry() )
				.documentNumber( aCitationDto.getDocumentNumber() )
				.documentKind( aCitationDto.getDocumentKind() )
				.documentPublicationDate( aCitationDto.getDocumentPublicationDate() )
				.documentName( aCitationDto.getDocumentName() )
				.citationText( aCitationDto.getCitationText() )
				.citationCategory( aCitationDto.getCitationCategory() )
				.relClaims( getClaims( aCitationDto.getRelClaims() ) )
				.build();
	}

	private String getClaims( final List< String > aClaims )
	{
		return String.join( ",", aClaims );
	}
}
