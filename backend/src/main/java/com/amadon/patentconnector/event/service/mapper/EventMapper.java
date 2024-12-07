package com.amadon.patentconnector.event.service.mapper;

import com.amadon.patentconnector.businessBranch.service.mapper.BusinessBranchMapper;
import com.amadon.patentconnector.event.entity.Event;
import com.amadon.patentconnector.event.service.dto.EventDto;
import com.amadon.patentconnector.event.service.dto.PersistEventDto;
import com.amadon.patentconnector.user.service.mapper.UserMapper;
import org.mapstruct.*;

@Mapper( unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = { UserMapper.class, BusinessBranchMapper.class } )
public interface EventMapper
{
	@Mapping( target = "caregiver", ignore = true )
	@Mapping( target = "businessBranches", ignore = true )
	Event toEntity( PersistEventDto eventDto );

	EventDto toDto( Event event );

	@Mapping( target = "caregiver", ignore = true )
	@Mapping( target = "businessBranches", ignore = true )
	@BeanMapping( nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE )
	Event partialUpdate( PersistEventDto eventDto, @MappingTarget Event event );
}