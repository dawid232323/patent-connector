package com.amadon.patentconnector.patent.service.dto.create;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.util.Set;

/**
 * DTO for {@link com.amadon.patentconnector.patent.entity.PatentBibliographicDatum}
 */
@AllArgsConstructor
@Getter
public class CreatePatentBibliographicDatumDto implements Serializable
{
	private final CreateApplicationReferenceDto applicationReference;
	private final CreateClassificationsIpcrDto classificationsIpcr;
	private final CreateDatesOfPublicAvailabilityDto datesOfPublicAvailability;
	private final String publicationDocumentCountry;
	private final String publicationDocumentNumber;
	private final String publicationDocumentKind;
	private final String publicationDocumentDate;
	private final String ipcClassificationEdition;
	private final String ipcMainClassification;
	private final String ipcFurtherClassifications;
	private final String cpcClassificationEdition;
	private final String cpcMainClassification;
	private final String cpcFurtherClassification;
	private final String inventionTitle;
	private final String inventionTitleEng;
	private final String claims;
	private final Set< CreatePatentAddressBookDto > agents;
	private final Set< CreatePatentAddressBookDto > applicants;
	private final Set< CreatePatentAddressBookDto > assignees;
	private final Set< CreatePatentAddressBookDto > inventors;
}