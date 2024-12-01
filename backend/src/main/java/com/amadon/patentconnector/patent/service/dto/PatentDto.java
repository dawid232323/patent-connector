package com.amadon.patentconnector.patent.service.dto;

import com.amadon.patentconnector.patent.service.dto.partials.PatentCitationDto;
import com.amadon.patentconnector.patent.service.dto.partials.PatentDocumentDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatentDto implements Serializable
{
	private String title;
	private String description;
	private String abstractField;
	private List<String> businessBranches;
	private List< PatentCitationDto> citations;
	private LocalDate beginDate;
	private LocalDate endDate;
	private String statusDescription;
	private String patentNumber;
	private List< PatentDocumentDto > documents;
}
