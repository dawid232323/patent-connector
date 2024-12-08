package com.amadon.patentconnector.event.service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class EventSearchQueryDto
{
	private String title;
	private LocalDateTime dateFrom;
	private LocalDateTime dateTo;
	private List< Long > businessBranchesIds;
	private Long caregiverId;
}
