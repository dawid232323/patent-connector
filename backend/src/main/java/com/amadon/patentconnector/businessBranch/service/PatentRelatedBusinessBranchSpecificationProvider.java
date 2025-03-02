package com.amadon.patentconnector.businessBranch.service;

import com.amadon.patentconnector.businessBranch.entity.BusinessBranch;
import com.amadon.patentconnector.businessBranch.entity.BusinessBranchType;
import com.amadon.patentconnector.businessBranch.service.repository.BusinessBranchRepository;
import com.amadon.patentconnector.patent.entity.Patent;
import com.amadon.patentconnector.patent.entity.PatentAnalysisDatum;
import com.amadon.patentconnector.patent.service.PatentRepository;
import com.amadon.patentconnector.shared.service.specification.SpecificationProvider;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class that resolves specification for patents assigned to specific business branches
 */
@Component
@RequiredArgsConstructor
public class PatentRelatedBusinessBranchSpecificationProvider implements SpecificationProvider< List< Long >, Patent >
{
	private final BusinessBranchRepository businessBranchRepository;
	private final BusinessBranchTypeResolver businessBranchTypeResolver;

	@Override
	public Specification< Patent > getSpecification( final List< Long > businessBranchesIds )
	{
		final List< BBTypeIdentity > branches = getBusinessBranches( businessBranchesIds );
		return ( ( root, query, criteriaBuilder ) ->
		{
			final Join< Patent, PatentAnalysisDatum > analysisDatumJoin = root.join( "patentAnalysisData" );
			final Join< PatentAnalysisDatum, BusinessBranch > businessBranchJoin = analysisDatumJoin.join(
					"businessBranches" );

			final List< Predicate > predicates = new ArrayList<>();

			for ( BBTypeIdentity bbTypeIdentity : branches )
			{
				predicates.add( criteriaBuilder
										.equal(
												criteriaBuilder.upper( criteriaBuilder.trim( businessBranchJoin.get( getBranchFieldName( bbTypeIdentity.type() ) ) ) ),
												getBranchFieldValue( bbTypeIdentity ) ) );
			}

			return criteriaBuilder.or( predicates.toArray( new Predicate[ 0 ] ) );
		} );
	}

	private String getBranchFieldName( final BusinessBranchType businessBranchType )
	{
		switch ( businessBranchType )
		{
			case SECTION ->
			{
				return BusinessBranchesFieldNames.SECTION;
			}
			case DEPARTMENT ->
			{
				return BusinessBranchesFieldNames.DEPARTMENT;
			}
			case GROUP ->
			{
				return BusinessBranchesFieldNames.GROUP;
			}
			case CLASS ->
			{
				return BusinessBranchesFieldNames.CLASS;
			}
			default ->
			{
				return BusinessBranchesFieldNames.CODE;
			}
		}
	}

	private String getBranchFieldValue( final BBTypeIdentity bbTypeIdentity )
	{
		final BusinessBranchType businessBranchType = bbTypeIdentity.type();
		final BusinessBranch businessBranch = bbTypeIdentity.businessBranch();
		return switch ( businessBranchType )
		{
			case SECTION -> businessBranch.getSection();
			case DEPARTMENT -> businessBranch.getDepartment();
			case GROUP -> businessBranch.getBusinessBranchGroup();
			case CLASS -> businessBranch.getBusinessBranchClass();
			default -> businessBranch.getCode();
		};
	}

	private List< BBTypeIdentity > getBusinessBranches( final List< Long > businessBranchesIds )
	{
		return businessBranchRepository.findAllByIdIn( businessBranchesIds )
				.stream()
				.map( branch ->
					  {
						  final BusinessBranchType type = businessBranchTypeResolver.resolveBranchType( branch );
						  return new BBTypeIdentity( type, branch );
					  } )
				.collect( Collectors.toList() );
	}

	private record BBTypeIdentity( BusinessBranchType type, BusinessBranch businessBranch )
	{
	}
}
