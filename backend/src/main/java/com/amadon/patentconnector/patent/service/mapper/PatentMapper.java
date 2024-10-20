package com.amadon.patentconnector.patent.service.mapper;


import com.amadon.patentconnector.patent.entity.Patent;
import com.amadon.patentconnector.patent.service.dto.create.CreatePatentDto;
import org.mapstruct.*;

@Mapper( unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING )
public interface PatentMapper
{
	@Mapping( target = "patentAnalysisData", ignore = true )
//	@Mapping( target = "bibliographicData", ignore = true )
	@Mapping( target = "searchReportData", ignore = true )
	@Mapping( target = "patentTimestamp", ignore = true )
	Patent fromCreateDto( CreatePatentDto aCreatePatentDto );
}
