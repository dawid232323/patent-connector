package com.amadon.patentconnector.patent.service.dto.create;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

/**
 * DTO for {@link com.amadon.patentconnector.patent.entity.PatentAddressBook}
 */
@AllArgsConstructor
@Getter
public class CreatePatentAddressBookDto implements Serializable
{
	private final String name;
	private final String firstName;
	private final String lastName;
	private final String organisationName;
	private final String addressPostalCode;
	private final String addressCity;
	private final String addressTownship;
	private final String addressCounty;
	private final String addressState;
	private final String addressCountry;
	private final String nationalityCountry;
	private final String residenceCountry;
	private final String designatedStates;
}