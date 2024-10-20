package com.amadon.patentconnector.patent.service.dto.create;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link com.amadon.patentconnector.patent.entity.PatentAddressBook}
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreatePatentAddressBookDto implements Serializable
{
	private String name;
	private String firstName;
	private String lastName;
	private String organisationName;
	private String addressPostalCode;
	private String addressCity;
	private String addressTownship;
	private String addressCounty;
	private String addressState;
	private String addressCountry;
	private String nationalityCountry;
	private String residenceCountry;
	private String designatedStates;
}