package com.amadon.patentconnector.patent.service.dto.create;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

/**
 * DTO for {@link com.amadon.patentconnector.patent.entity.PatentBibliographicDatum}
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreatePatentBibliographicDatumDto implements Serializable
{
	private CreateApplicationReferenceDto applicationReference;
	private CreateDatesOfPublicAvailabilityDto datesOfPublicAvailability;
	private String publicationDocumentCountry;
	private String publicationDocumentNumber;
	private String publicationDocumentKind;
	private String publicationDocumentDate;
	private String ipcClassificationEdition;
	private String ipcMainClassification;
	private String ipcFurtherClassifications;
	private String cpcClassificationEdition;
	private String cpcMainClassification;
	private String cpcFurtherClassification;
	private String inventionTitle;
	private String inventionTitleEng;
	private String claims;
	private Set< CreatePatentAddressBookDto > agents;
	private Set< CreatePatentAddressBookDto > applicants;
	private Set< CreatePatentAddressBookDto > assignees;
	private Set< CreatePatentAddressBookDto > inventors;
}