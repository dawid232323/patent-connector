package com.amadon.patentconnector.patent.service.dto.create;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

/**
 * DTO for {@link com.amadon.patentconnector.patent.entity.ApplicationReference}
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateApplicationReferenceDto implements Serializable
{
	private String documentCountryId;
	private String documentNumber;
	private String documentKind;
	private String documentDate;
	private Set< CreateOtherPatentDocumentDto > otherPatentDocuments;
}