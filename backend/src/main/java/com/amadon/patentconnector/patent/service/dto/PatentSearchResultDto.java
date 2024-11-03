package com.amadon.patentconnector.patent.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatentSearchResultDto
{
	private Long id;
	private String name;
	private String patentAbstract;
	private LocalDate dateFrom;
	private LocalDateTime patentTimestamp;
	private String statusDescription;
	private List< String > businessBranches;
}
