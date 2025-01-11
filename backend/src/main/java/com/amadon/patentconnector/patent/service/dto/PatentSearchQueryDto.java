package com.amadon.patentconnector.patent.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
public class PatentSearchQueryDto
{
	private String title;
	private List<Long> businessBranchesIds;
	private LocalDate dateCreated;
}
