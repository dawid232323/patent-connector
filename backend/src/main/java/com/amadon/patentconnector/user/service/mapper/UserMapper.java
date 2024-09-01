package com.amadon.patentconnector.user.service.mapper;

import com.amadon.patentconnector.researchInstitution.service.mapper.ResearchInstitutionMapper;
import com.amadon.patentconnector.user.entity.User;
import com.amadon.patentconnector.user.features.entrepreneurData.entity.EntrepreneursData;
import com.amadon.patentconnector.user.features.entrepreneurData.service.mapper.EntrepreneursDataMapper;
import com.amadon.patentconnector.user.service.dto.CreateResearchInstitutionWorkerDto;
import com.amadon.patentconnector.user.service.dto.CreateUserDto;
import com.amadon.patentconnector.user.service.dto.UserDto;
import org.mapstruct.*;

@Mapper( unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING,
		uses = { EntrepreneursDataMapper.class, ResearchInstitutionMapper.class } )
public interface UserMapper
{
	@Mapping( target = "name", source = "firstName" )
	User fromCreateToEntity( CreateUserDto createUserDto );

	@Mapping( target = "researchInstitution", ignore = true )
	@Mapping( source = "firstName", target = "name" )
	User fromCreateInstitutionWorker( CreateResearchInstitutionWorkerDto aInstitutionWorkerDto );

	User toEntity( UserDto userDto );

	@AfterMapping
	default void linkEntrepreneursData( @MappingTarget User user )
	{
		EntrepreneursData entrepreneursData = user.getEntrepreneursData();
		if ( entrepreneursData != null )
		{
			entrepreneursData.setUser( user );
		}
	}

	@Mapping( source = "name", target = "firstName" )
	@Mapping( target = "roles", expression = "java(user.getAuthorities())" )
	UserDto toDto( User user );

	@BeanMapping( nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE )
	User partialUpdate( UserDto userDto, @MappingTarget User user );
}