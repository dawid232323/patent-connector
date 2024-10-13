package com.amadon.patentconnector.patent.service.mapper;

import com.amadon.patentconnector.patent.entity.DatesOfPublicAvailability;
import com.amadon.patentconnector.patent.service.dto.create.CreateDatesOfPublicAvailabilityDto;
import org.mapstruct.*;

@Mapper( unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING )
public interface DatesOfPublicAvailabilityMapper
{
	DatesOfPublicAvailability toEntityFromCreate( CreateDatesOfPublicAvailabilityDto createDatesOfPublicAvailabilityDto );

	@BeanMapping( nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE )
	DatesOfPublicAvailability partialUpdate( CreateDatesOfPublicAvailabilityDto createDatesOfPublicAvailabilityDto,
											 @MappingTarget DatesOfPublicAvailability datesOfPublicAvailability );
}