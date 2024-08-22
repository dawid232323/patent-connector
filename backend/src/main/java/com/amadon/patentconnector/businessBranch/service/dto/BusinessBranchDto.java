package com.amadon.patentconnector.businessBranch.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

/**
 * DTO for {@link com.amadon.patentconnector.businessBranch.entity.BusinessBranch}
 */
@AllArgsConstructor
@Getter
public class BusinessBranchDto implements Serializable
{
	private final Long id;
	private final String section;
	private final String department;
	private final String businessBranchGroup;
	private final String businessBranchClass;
	private final String code;
	private final String displayName;
}