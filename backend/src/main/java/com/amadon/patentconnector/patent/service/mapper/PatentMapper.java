package com.amadon.patentconnector.patent.service.mapper;


import com.amadon.patentconnector.patent.entity.Patent;
import com.amadon.patentconnector.patent.service.dto.PatentDto;
import com.amadon.patentconnector.patent.service.dto.PatentSearchResultDto;
import com.amadon.patentconnector.patent.service.dto.create.CreatePatentDto;
import org.mapstruct.*;

@Mapper( unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING )
@DecoratedWith( PatentMapperDecorator.class )
public interface PatentMapper
{
	@Mapping( target = "patentAnalysisData", ignore = true )
//	@Mapping( target = "bibliographicData", ignore = true )
	@Mapping( target = "searchReportData", ignore = true )
	@Mapping( target = "patentTimestamp", ignore = true )
	Patent fromCreateDto( CreatePatentDto aCreatePatentDto );

	@Mapping( target = "name", ignore = true )
	@Mapping( target = "businessBranches", ignore = true )
	@Mapping( target = "dateFrom", source = "beginDate" )
	@Mapping( target = "patentAbstract", source = "abstractField" )
	PatentSearchResultDto toPatentSearchResultDto( Patent patent );

	@Mapping( target = "title", ignore = true )
	@Mapping( target = "businessBranches", ignore = true )
	@Mapping( target = "citations", ignore = true )
	@Mapping( target = "patentNumber", ignore = true )
	@Mapping( target = "documents", ignore = true )
	@Mapping( target = "usageDescriptions", ignore = true )
	PatentDto fromEntityToDto( Patent patent );
}
