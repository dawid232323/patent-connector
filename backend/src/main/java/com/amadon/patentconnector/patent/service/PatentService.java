package com.amadon.patentconnector.patent.service;

import com.amadon.patentconnector.patent.entity.Patent;
import com.amadon.patentconnector.patent.entity.PatentAnalysisDatum;
import com.amadon.patentconnector.patent.entity.PatentBibliographicDatum;
import com.amadon.patentconnector.patent.entity.PatentSearchReportDatum;
import com.amadon.patentconnector.patent.service.componentCreator.PatentComponentCreator;
import com.amadon.patentconnector.patent.service.dto.create.CreatePatentAnalysisDatumDto;
import com.amadon.patentconnector.patent.service.dto.create.CreatePatentBibliographicDatumDto;
import com.amadon.patentconnector.patent.service.dto.create.CreatePatentDto;
import com.amadon.patentconnector.patent.service.dto.create.CretaePatentSearchReportDatumDto;
import com.amadon.patentconnector.patent.service.mapper.PatentMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PatentService
{
	private final PatentMapper patentMapper;
	private final PatentRepository patentRepository;
	private final PatentComponentCreator< CreatePatentAnalysisDatumDto, PatentAnalysisDatum > analysisCreator;
	private final PatentComponentCreator< CreatePatentBibliographicDatumDto, PatentBibliographicDatum > bibliographicCreator;
	private final PatentComponentCreator< CretaePatentSearchReportDatumDto, PatentSearchReportDatum > searchReportCreator;

	public List< Object > createPatents( final List< CreatePatentDto > aCreatePatentDtos )
	{
		log.info( "Started patent creation process for {} entries", aCreatePatentDtos.size() );
		int createdPatents = 0;
		for ( final CreatePatentDto createPatentDto: aCreatePatentDtos )
		{
			try
			{
				final Patent createdPatent = createPatent( createPatentDto );
				patentRepository.save( createdPatent );
				createdPatents += 1;
				log.info( "Successfully created patent {}", createdPatent.getTitle() );
			} catch ( Exception e )
			{
				log.error( "Error while creating patent with title {}", createPatentDto.getTitle(), e );
			}

		}
		log.info( "Successfully created {} patent entries", createdPatents );
		if ( aCreatePatentDtos.size() > createdPatents )
		{
			log.warn( "Missed {} entries. Please refer to earlier logs for more details", aCreatePatentDtos.size() - createdPatents );
		}
		return new ArrayList<>();
	}

	private Patent createPatent( final CreatePatentDto aCreatePatentDto )
	{
		log.info( "Creating patent with title {}", aCreatePatentDto.getTitle() );

		final Patent patent = patentMapper.fromCreateDto( aCreatePatentDto );
		final PatentAnalysisDatum patentAnalysisDatum = analysisCreator.resolvePatentComponent( aCreatePatentDto.getPatentAnalysisData() );
		final PatentBibliographicDatum bibliographicDatum = bibliographicCreator.resolvePatentComponent( aCreatePatentDto.getBibliographicData() );
		final PatentSearchReportDatum searchReportDatum = searchReportCreator.resolvePatentComponent( aCreatePatentDto.getSearchReportData() );

		patent.setPatentAnalysisData( patentAnalysisDatum );
		patentAnalysisDatum.setPatent( patent );
		patent.setBibliographicData( bibliographicDatum );
		bibliographicDatum.setPatent( patent );
		patent.setSearchReportData( searchReportDatum );
		searchReportDatum.setPatent( patent );

		return patent;
	}
}
