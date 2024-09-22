package com.amadon.patentconnector.patent.service.dto.create;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link com.amadon.patentconnector.patent.entity.PatentCitation}
 */
@AllArgsConstructor
@Getter
public class CreatePatentCitationDto implements Serializable
{
	private final String documentCountry;
	private final String documentNumber;
	private final String documentKind;
	private final String documentPublicationDate;
	private final String documentName;
	private final String citationText;
	private final String citationCategory;
	private final List< String > relClaims;
}