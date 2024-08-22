package com.amadon.patentconnector.businessBranch.service.mapper;

import com.amadon.patentconnector.businessBranch.entity.BusinessBranch;
import com.amadon.patentconnector.businessBranch.service.dto.BusinessBranchDto;
import org.mapstruct.*;

@Mapper( unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING )
public interface BusinessBranchMapper
{
	BusinessBranch toEntity( BusinessBranchDto businessBranchDto );

	BusinessBranchDto toDto( BusinessBranch businessBranch );

	@BeanMapping( nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE )
	BusinessBranch partialUpdate( BusinessBranchDto businessBranchDto, @MappingTarget BusinessBranch businessBranch );
}