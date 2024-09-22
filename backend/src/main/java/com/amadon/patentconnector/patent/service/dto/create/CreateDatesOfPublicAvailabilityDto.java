package com.amadon.patentconnector.patent.service.dto.create;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

/**
 * DTO for {@link com.amadon.patentconnector.patent.entity.DatesOfPublicAvailability}
 */
@AllArgsConstructor
@Getter
public class CreateDatesOfPublicAvailabilityDto implements Serializable
{
	private final String unexaminedPrintedWithoutGrantDocumentCountry;
	private final String unexaminedPrintedWithoutGrantDocumentNumber;
	private final String unexaminedPrintedWithoutGrantDocumentDate;
}