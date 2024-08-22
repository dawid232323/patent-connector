package com.amadon.patentconnector.businessBranch.controller;

import com.amadon.patentconnector.businessBranch.service.BusinessBranchService;
import com.amadon.patentconnector.businessBranch.service.dto.BusinessBranchDto;
import com.amadon.patentconnector.shared.constants.AppEndpoints;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping( value = AppEndpoints.BusinessBranchEndpoints.businessBranchBase,
		produces = MediaType.APPLICATION_JSON_VALUE,
		consumes = MediaType.APPLICATION_JSON_VALUE )
public class BusinessBranchController
{

	private final BusinessBranchService businessBranchService;

	@ResponseBody
	@GetMapping( AppEndpoints.BusinessBranchEndpoints.sectionBusinessBranches )
	public List< BusinessBranchDto > getAllSectionBusinessBranches()
	{
		return businessBranchService.getAllSectionBusinessBranches();
	}
}
