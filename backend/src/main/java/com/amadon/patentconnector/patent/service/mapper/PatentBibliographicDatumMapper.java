package com.amadon.patentconnector.patent.service.mapper;

import com.amadon.patentconnector.patent.entity.ClassificationsIpcr;
import com.amadon.patentconnector.patent.entity.DatesOfPublicAvailability;
import com.amadon.patentconnector.patent.entity.PatentBibliographicDatum;
import com.amadon.patentconnector.patent.service.dto.create.CreatePatentBibliographicDatumDto;
import org.mapstruct.*;

@Mapper( unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING,
		uses = { DatesOfPublicAvailabilityMapper.class, ClassificationsIpcrMapper.class } )
public interface PatentBibliographicDatumMapper
{
	@Mapping( target = "agents", ignore = true )
	@Mapping( target = "applicants", ignore = true )
	@Mapping( target = "assignees", ignore = true )
	@Mapping( target = "inventors", ignore = true )
	PatentBibliographicDatum toEntityFromCreate( CreatePatentBibliographicDatumDto createPatentBibliographicDatumDto );

	@AfterMapping
	default void linkClassificationsIpcr( @MappingTarget PatentBibliographicDatum patentBibliographicDatum )
	{
		ClassificationsIpcr classificationsIpcr = patentBibliographicDatum.getClassificationsIpcr();
		if ( classificationsIpcr != null )
		{
			classificationsIpcr.setBibliographicData( patentBibliographicDatum );
		}
	}

	@AfterMapping
	default void linkDatesOfPublicAvailability( @MappingTarget PatentBibliographicDatum patentBibliographicDatum )
	{
		DatesOfPublicAvailability datesOfPublicAvailability = patentBibliographicDatum.getDatesOfPublicAvailability();
		if ( datesOfPublicAvailability != null )
		{
			datesOfPublicAvailability.setBibliographicData( patentBibliographicDatum );
		}
	}

	@BeanMapping( nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE )
	PatentBibliographicDatum partialUpdate( CreatePatentBibliographicDatumDto createPatentBibliographicDatumDto,
											@MappingTarget PatentBibliographicDatum patentBibliographicDatum );
}