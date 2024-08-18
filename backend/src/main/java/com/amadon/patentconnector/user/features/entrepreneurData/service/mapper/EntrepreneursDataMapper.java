package com.amadon.patentconnector.user.features.entrepreneurData.service.mapper;

import com.amadon.patentconnector.user.features.entrepreneurData.entity.EntrepreneursData;
import com.amadon.patentconnector.user.features.entrepreneurData.service.dto.CreateEntrepreneursDataDto;
import org.mapstruct.*;

@Mapper( unmappedTargetPolicy = ReportingPolicy.IGNORE )
public interface EntrepreneursDataMapper
{
	EntrepreneursData toEntityFromCreateDto( CreateEntrepreneursDataDto createEntrepreneursDataDto );
}