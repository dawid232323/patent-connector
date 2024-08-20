package com.amadon.patentconnector.researchInstitution.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

/**
 * DTO for {@link com.amadon.patentconnector.researchInstitution.entity.ResearchInstitution}
 */
@AllArgsConstructor
@Getter
public class ResearchInstitutionDto implements Serializable
{
	private final Long id;
	private final String uuid;
	private final String name;
	private final String supervisor;
	private final String kind;
	private final Boolean isNationalResearchInstitute;
	private final String type;
	private final String regon;
	private final String nip;
	private final String email;
	private final String country;
	private final String postalCode;
	private final String state;
	private final String city;
}