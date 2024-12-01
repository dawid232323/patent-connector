package com.amadon.patentconnector.patent.service.dto.partials;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatentCitationDto
{
	private String documentNumber;
	private String documentName;
	private String citationText;
	private String publicationDate;
}
