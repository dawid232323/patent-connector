package com.amadon.patentconnector.inventionDemand.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventionDemandDto
{
	private Long issuerId;
	private Long recipientId;
	private String content;
	private List< Long > businessBranchesIds;
}
