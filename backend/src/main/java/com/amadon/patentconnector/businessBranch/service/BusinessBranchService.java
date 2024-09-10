package com.amadon.patentconnector.businessBranch.service;

import com.amadon.patentconnector.businessBranch.entity.BusinessBranch;
import com.amadon.patentconnector.businessBranch.service.dto.BusinessBranchDto;
import com.amadon.patentconnector.businessBranch.service.mapper.BusinessBranchMapper;
import com.amadon.patentconnector.businessBranch.service.repository.BusinessBranchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BusinessBranchService
{
	private final BusinessBranchRepository businessBranchRepository;
	private final BusinessBranchMapper businessBranchMapper;

	public List< BusinessBranchDto > getAllSectionBusinessBranches()
	{
		return businessBranchRepository.resolveAllSectionBusinessBranches()
				.stream()
				.map( businessBranchMapper::toDto )
				.collect( Collectors.toList() );
	}

	public List< BusinessBranch > getBusinessBranchesByIdIn( final List< Long > aIds )
	{
		return businessBranchRepository.findAllByIdIn( aIds );
	}
}
