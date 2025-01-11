package com.amadon.patentconnector.patent.service.dto.create;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CreatePatentUsageDescriptionDto
{
	private String branchCode;
	private List< String > descriptions;
}
