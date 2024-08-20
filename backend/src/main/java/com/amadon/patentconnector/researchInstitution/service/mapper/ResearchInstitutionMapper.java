package com.amadon.patentconnector.researchInstitution.service.mapper;

import com.amadon.patentconnector.researchInstitution.entity.ResearchInstitution;
import com.amadon.patentconnector.researchInstitution.service.dto.ResearchInstitutionDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper( unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING )
public interface ResearchInstitutionMapper
{
	ResearchInstitutionDto fromEntity( ResearchInstitution aResearchInstitution );
}
