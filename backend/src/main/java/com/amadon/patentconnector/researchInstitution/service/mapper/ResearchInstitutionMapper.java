package com.amadon.patentconnector.researchInstitution.service.mapper;

import com.amadon.patentconnector.researchInstitution.entity.ResearchInstitution;
import com.amadon.patentconnector.researchInstitution.service.dto.ResearchInstitutionDto;
import org.mapstruct.*;

@Mapper( unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING )
public interface ResearchInstitutionMapper
{
	ResearchInstitutionDto fromEntity( ResearchInstitution aResearchInstitution );

	ResearchInstitution toEntity( ResearchInstitutionDto researchInstitutionDto );

	@BeanMapping( nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE )
	ResearchInstitution partialUpdate( ResearchInstitutionDto researchInstitutionDto, @MappingTarget ResearchInstitution researchInstitution );
}
