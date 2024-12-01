package com.amadon.patentconnector.patent.service;

import com.amadon.patentconnector.patent.entity.Patent;
import com.amadon.patentconnector.patent.entity.PatentAnalysisDatum;
import com.amadon.patentconnector.patent.entity.PatentBibliographicDatum;
import com.amadon.patentconnector.patent.entity.PatentSearchReportDatum;
import com.amadon.patentconnector.patent.service.componentCreator.PatentComponentCreator;
import com.amadon.patentconnector.patent.service.dto.PatentDto;
import com.amadon.patentconnector.patent.service.dto.PatentSearchQueryDto;
import com.amadon.patentconnector.patent.service.dto.PatentSearchResultDto;
import com.amadon.patentconnector.patent.service.dto.create.CreatePatentAnalysisDatumDto;
import com.amadon.patentconnector.patent.service.dto.create.CreatePatentBibliographicDatumDto;
import com.amadon.patentconnector.patent.service.dto.create.CreatePatentDto;
import com.amadon.patentconnector.patent.service.dto.create.CretaePatentSearchReportDatumDto;
import com.amadon.patentconnector.patent.service.mapper.PatentMapper;
import com.amadon.patentconnector.shared.service.specification.SpecificationProvider;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
	private final SpecificationProvider< PatentSearchQueryDto, Patent > specificationProvider;

	public List< PatentDto > createPatents( final List< CreatePatentDto > aCreatePatentDtos )
	{
		log.info( "Started patent creation process for {} entries", aCreatePatentDtos.size() );
		final List<Patent> createdPatents = new ArrayList<>();
		for ( final CreatePatentDto createPatentDto : aCreatePatentDtos )
		{
			try
			{
				final Patent createdPatent = createPatent( createPatentDto );
				patentRepository.save( createdPatent );
				createdPatents.add( createdPatent );
				log.info( "Successfully created patent {}", createdPatent.getTitle() );
			} catch ( Exception e )
			{
				log.error( "Error while creating patent with title {}", createPatentDto.getTitle(), e );
			}

		}
		log.info( "Successfully created {} patent entries", createdPatents );
		if ( aCreatePatentDtos.size() > createdPatents.size() )
		{
			log.warn( "Missed {} entries. Please refer to earlier logs for more details",
					  aCreatePatentDtos.size() - createdPatents.size() );
		}
		return createdPatents.stream()
				.map( patentMapper::fromEntityToDto )
				.collect( Collectors.toList() );
	}

	public Page< PatentSearchResultDto > findPatents( final PatentSearchQueryDto aPatentSearchQueryDto,
													  final Pageable aPage )
	{
		final Specification< Patent > specification = specificationProvider.getSpecification( aPatentSearchQueryDto );
		return patentRepository.findAll( specification, aPage )
				.map( patentMapper::toPatentSearchResultDto );
	}

	public PatentDto getPatent( final Long id )
	{
		log.info( "Going to retrieve patent with id {}", id );
		log.warn( patentRepository.findById( id ).get().getStatusId() );
		return patentMapper.fromEntityToDto( patentRepository.getPatent( id ) );
	}

	private Patent createPatent( final CreatePatentDto aCreatePatentDto )
	{
		log.info( "Creating patent with title {}", aCreatePatentDto.getTitle() );

		final Patent patent = patentMapper.fromCreateDto( aCreatePatentDto );
		final PatentAnalysisDatum patentAnalysisDatum =
				analysisCreator.resolvePatentComponent( aCreatePatentDto.getPatentAnalysisData() );
		final PatentBibliographicDatum bibliographicDatum =
				bibliographicCreator.resolvePatentComponent( aCreatePatentDto.getBibliographicData() );
		final PatentSearchReportDatum searchReportDatum =
				searchReportCreator.resolvePatentComponent( aCreatePatentDto.getSearchReportData() );

		patent.setPatentAnalysisData( patentAnalysisDatum );
		patentAnalysisDatum.setPatent( patent );
		patent.setBibliographicData( bibliographicDatum );
		bibliographicDatum.setPatent( patent );
		patent.setSearchReportData( searchReportDatum );
		searchReportDatum.setPatent( patent );

		return patent;
	}
}
