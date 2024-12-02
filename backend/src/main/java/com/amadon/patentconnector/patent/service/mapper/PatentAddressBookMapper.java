package com.amadon.patentconnector.patent.service.mapper;

import com.amadon.patentconnector.patent.entity.PatentAddressBook;
import com.amadon.patentconnector.patent.service.dto.PatentAddressBookDto;
import com.amadon.patentconnector.patent.service.dto.create.CreatePatentAddressBookDto;
import org.mapstruct.*;

@Mapper( unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING,
		nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS )
public interface PatentAddressBookMapper
{
	PatentAddressBook toEntityFromCreateDto( CreatePatentAddressBookDto createPatentAddressBookDto );

	@Mapping( target = "postalCode", source = "addressPostalCode" )
	@Mapping( target = "country", source = "addressCountry" )
	@Mapping( target = "city", source = "addressCity" )
	@Mapping( target = "nationality", source = "nationalityCountry" )
	PatentAddressBookDto toDtoFromEntity( PatentAddressBook patentAddressBook );

	@BeanMapping( nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE )
	PatentAddressBook partialUpdate( CreatePatentAddressBookDto createPatentAddressBookDto,
									 @MappingTarget PatentAddressBook patentAddressBook );
}