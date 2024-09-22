package com.amadon.patentconnector.patent.service.dto.create;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.util.Set;

/**
 * DTO for {@link com.amadon.patentconnector.patent.entity.ApplicationReference}
 */
@AllArgsConstructor
@Getter
public class CreateApplicationReferenceDto implements Serializable
{
	private final String documentCountryId;
	private final String documentNumber;
	private final String documentKind;
	private final String documentDate;
	private final Set< CreateOtherPatentDocumentDto > otherPatentDocuments;
}