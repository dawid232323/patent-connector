package com.amadon.patentconnector.businessBranch.service;

import com.amadon.patentconnector.businessBranch.entity.BusinessBranch;
import com.amadon.patentconnector.businessBranch.service.dto.BusinessBranchDto;
import com.amadon.patentconnector.businessBranch.service.mapper.BusinessBranchMapper;
import com.amadon.patentconnector.businessBranch.service.repository.BusinessBranchRepository;
import com.amadon.patentconnector.shared.service.specification.SpecificationProvider;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
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
	private final SpecificationProvider< BusinessBranch, BusinessBranch > specificationProvider;

	public List< BusinessBranchDto > getAllSectionBusinessBranches()
	{
		return businessBranchRepository.resolveAllSectionBusinessBranches()
				.stream()
				.map( businessBranchMapper::toDto )
				.collect( Collectors.toList() );
	}

	public List< BusinessBranchDto > getAllSpecificBusinessBranches()
	{
		return businessBranchRepository.resolveAllSpecificBusinessBranches()
				.stream()
				.map( businessBranchMapper::toDto )
				.collect( Collectors.toList() );
	}

	public List< BusinessBranch > getBusinessBranchesByIdIn( final List< Long > aIds )
	{
		return businessBranchRepository.findAllByIdIn( aIds );
	}

	public List< BusinessBranch > getBusinessBranchesForCodes( final List< String > aBranchCodes )
	{
		return businessBranchRepository.resolveAllBranchesByCodeIn( aBranchCodes );
	}

	public List< BusinessBranchDto > getBusinessBranchChildren( final Long id )
	{
		final BusinessBranch businessBranch = businessBranchRepository.findById( id )
				.orElseThrow( EntityNotFoundException::new );
		final Specification< BusinessBranch > specification = specificationProvider.getSpecification( businessBranch );
		return businessBranchRepository.findAll( specification )
				.stream()
				.map( businessBranchMapper::toDto )
				.collect( Collectors.toList() );
	}
}
