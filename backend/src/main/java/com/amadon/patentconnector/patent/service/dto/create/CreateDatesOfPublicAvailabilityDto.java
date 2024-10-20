package com.amadon.patentconnector.patent.service.dto.create;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link com.amadon.patentconnector.patent.entity.DatesOfPublicAvailability}
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateDatesOfPublicAvailabilityDto implements Serializable
{
	private String unexaminedPrintedWithoutGrantDocumentCountry;
	private String unexaminedPrintedWithoutGrantDocumentNumber;
	private String unexaminedPrintedWithoutGrantDocumentDate;
}