package com.amadon.patentconnector.patent.service.dto.create;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link com.amadon.patentconnector.patent.entity.PatentCitation}
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreatePatentCitationDto implements Serializable
{
	private String documentCountry;
	private String documentNumber;
	private String documentKind;
	private String documentPublicationDate;
	private String documentName;
	private String citationText;
	private String citationCategory;
	private List< String > relClaims;
}