package com.amadon.patentconnector.user.features.entrepreneurData.service.dto;

import com.amadon.patentconnector.businessBranch.service.dto.BusinessBranchDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class EntrepreneursDataDto
{
	private final String companyName;
	private final String nip;
	private final String regon;
	private final Boolean recommendationAgreement;
	private final List< BusinessBranchDto > interestBranches;
}
