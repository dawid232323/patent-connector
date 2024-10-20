package com.amadon.patentconnector.patent.service.dto.create;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.amadon.patentconnector.patent.entity.Patent}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePatentDto implements Serializable
{
	private CreatePatentAnalysisDatumDto patentAnalysisData;
	private CreatePatentBibliographicDatumDto bibliographicData;
	private CretaePatentSearchReportDatumDto searchReportData;
	private String title;
	private String description;
	private String abstractField;
	private String source;
	@JsonFormat(pattern = "yyyy-MM-dd:HH:mm:ss")
	private LocalDateTime patentTimestamp;
	private LocalDate beginDate;
	private LocalDate endDate;
	private String statusId;
	private String statusDescription;
	private String statusCode;
	private String extidappli;
	private String extidpatent;
	private String cntrenew;
	private String gazetteNumber;
	private String gazetteNoSpec;
	private String gazetteKind;
	private LocalDate gazetteDate;
	private String gazetteUri;
}