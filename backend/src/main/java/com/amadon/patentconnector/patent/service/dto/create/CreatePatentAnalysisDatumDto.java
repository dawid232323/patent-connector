package com.amadon.patentconnector.patent.service.dto.create;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * DTO for {@link com.amadon.patentconnector.patent.entity.PatentAnalysisDatum}
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreatePatentAnalysisDatumDto implements Serializable
{
	private Set< String > businessBranchesCodes;
	private Set< CreatePatentUsageDescriptionDto > usageDescriptions;
}