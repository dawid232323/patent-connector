package com.amadon.patentconnector.patent.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PatentSearchQueryDto
{
	private String title;
	private List<Long> businessBranchesIds;
	private LocalDate dateCreated;
}
