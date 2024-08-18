package com.amadon.patentconnector.user.service.mapper;

import com.amadon.patentconnector.user.entity.User;
import com.amadon.patentconnector.user.features.entrepreneurData.entity.EntrepreneursData;
import com.amadon.patentconnector.user.features.entrepreneurData.service.mapper.EntrepreneursDataMapper;
import com.amadon.patentconnector.user.service.dto.CreateUserDto;
import org.mapstruct.*;

@Mapper( unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING,
		uses = { EntrepreneursDataMapper.class } )
public interface UserMapper
{
	User fromCreateToEntity( CreateUserDto createUserDto );
}