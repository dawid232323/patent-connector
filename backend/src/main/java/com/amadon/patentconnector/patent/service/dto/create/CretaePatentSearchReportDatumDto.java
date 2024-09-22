package com.amadon.patentconnector.patent.service.dto.create;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.util.Set;

/**
 * DTO for {@link com.amadon.patentconnector.patent.entity.PatentSearchReportDatum}
 */
@AllArgsConstructor
@Getter
public class CretaePatentSearchReportDatumDto implements Serializable
{
	private final Set< CreatePatentCitationDto > patentCitations;
}