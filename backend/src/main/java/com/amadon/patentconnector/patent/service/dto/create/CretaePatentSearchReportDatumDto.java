package com.amadon.patentconnector.patent.service.dto.create;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

/**
 * DTO for {@link com.amadon.patentconnector.patent.entity.PatentSearchReportDatum}
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CretaePatentSearchReportDatumDto implements Serializable
{
	private Set< CreatePatentCitationDto > patentCitations;
}