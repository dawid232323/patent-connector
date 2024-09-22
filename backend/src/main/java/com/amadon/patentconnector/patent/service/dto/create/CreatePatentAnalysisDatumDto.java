package com.amadon.patentconnector.patent.service.dto.create;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * DTO for {@link com.amadon.patentconnector.patent.entity.PatentAnalysisDatum}
 */
@AllArgsConstructor
@Getter
public class CreatePatentAnalysisDatumDto implements Serializable
{
	private final Set< String > businessBranchesCodes;
}