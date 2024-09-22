package com.amadon.patentconnector.patent.service.dto.create;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;

/**
 * DTO for {@link com.amadon.patentconnector.patent.entity.Patent}
 */
@AllArgsConstructor
@Getter
public class CreatePatentDto implements Serializable
{
	private final CreatePatentAnalysisDatumDto patentAnalysisData;
	private final CreatePatentBibliographicDatumDto bibliographicData;
	private final CretaePatentSearchReportDatumDto searchReportData;
	private final String title;
	private final String description;
	private final String abstractField;
	private final String source;
	private final Instant patentTimestamp;
	private final LocalDate beginDate;
	private final LocalDate endDate;
	private final String statusId;
	private final String statusDescription;
	private final String statusCode;
	private final String extidappli;
	private final String extidpatent;
	private final String cntrenew;
	private final String gazetteNumber;
	private final String gazetteNoSpec;
	private final String gazetteKind;
	private final LocalDate gazetteDate;
	private final String gazetteUri;
}