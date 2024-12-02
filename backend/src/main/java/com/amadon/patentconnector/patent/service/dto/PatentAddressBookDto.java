package com.amadon.patentconnector.patent.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatentAddressBookDto implements Serializable
{
	private String name;
	private String firstName;
	private String lastName;
	private String organisationName;
	private String postalCode;
	private String country;
	private String city;
	private String nationality;
}
