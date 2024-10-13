package com.amadon.patentconnector.patent.service.mapper;

import com.amadon.patentconnector.patent.entity.ClassificationsIpcr;
import com.amadon.patentconnector.patent.service.dto.create.CreateClassificationsIpcrDto;
import org.mapstruct.*;

@Mapper( unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING )
public interface ClassificationsIpcrMapper
{
	ClassificationsIpcr toEntityFromCreate( CreateClassificationsIpcrDto createClassificationsIpcrDto );

	@BeanMapping( nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE )
	ClassificationsIpcr partialUpdate( CreateClassificationsIpcrDto createClassificationsIpcrDto,
									   @MappingTarget ClassificationsIpcr classificationsIpcr );
}