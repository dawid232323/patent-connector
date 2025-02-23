package com.amadon.patentconnector.businessBranch.service;

import com.amadon.patentconnector.businessBranch.entity.BusinessBranch;
import com.amadon.patentconnector.businessBranch.entity.BusinessBranchType;
import com.amadon.patentconnector.businessBranch.service.repository.BusinessBranchRepository;
import com.amadon.patentconnector.shared.service.specification.SpecificationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BusinesssBranchSpecificationProvider implements SpecificationProvider< BusinessBranch, BusinessBranch >
{
	private final BusinessBranchTypeResolver typeResolver;

	@Override
	public Specification< BusinessBranch > getSpecification( final BusinessBranch businessBranch )
	{
		final BusinessBranchType businessBranchType = typeResolver.resolveBranchType( businessBranch );
		return switch ( businessBranchType )
		{
			case SECTION -> getSectionChildren( businessBranch.getSection() );
			case DEPARTMENT -> getDepartmentChildren( businessBranch.getDepartment() );
			case GROUP -> getGroupChildren( businessBranch.getBusinessBranchGroup() );
			case CLASS -> getClassChildren( businessBranch.getBusinessBranchClass() );
			default -> noSpec();
		};
	}

	private Specification< BusinessBranch > getSectionChildren( final String code )
	{
		return codeEquals( BusinessBranchesFieldNames.SECTION, code )
				.and( codeNotEmpty( BusinessBranchesFieldNames.DEPARTMENT ) )
				.and( codeEmpty( BusinessBranchesFieldNames.GROUP ) )
				.and( codeEmpty( BusinessBranchesFieldNames.CLASS ) )
				.and( codeEmpty( BusinessBranchesFieldNames.CODE ) );
	}

	private Specification< BusinessBranch > getDepartmentChildren( final String code )
	{
		return codeEquals( BusinessBranchesFieldNames.DEPARTMENT, code )
				.and( codeNotEmpty( BusinessBranchesFieldNames.GROUP ) )
				.and( codeEmpty( BusinessBranchesFieldNames.CLASS ) )
				.and( codeEmpty( BusinessBranchesFieldNames.CODE ) );
	}

	private Specification< BusinessBranch > getGroupChildren( final String code )
	{
		return codeEquals( BusinessBranchesFieldNames.GROUP, code )
				.and( codeNotEmpty( BusinessBranchesFieldNames.CLASS ) )
				.and( codeEmpty( BusinessBranchesFieldNames.CODE ) );
	}

	private Specification< BusinessBranch > getClassChildren( final String code )
	{
		return codeEquals( BusinessBranchesFieldNames.CLASS, code )
				.and( codeNotEmpty( BusinessBranchesFieldNames.CODE ) );
	}

	private Specification< BusinessBranch > codeEquals( final String attributeName, final String attributeValue )
	{
		return ( ( root, query, criteriaBuilder ) ->
				criteriaBuilder.equal( criteriaBuilder.upper( criteriaBuilder.trim( root.get( attributeName ) ) ),
									   attributeValue.strip()
											   .toUpperCase() ) );
	}

	private Specification< BusinessBranch > codeEmpty( final String attributeName )
	{
		return ( ( root, query, criteriaBuilder ) -> criteriaBuilder.isNull( root.get( attributeName ) ) );
	}

	private Specification< BusinessBranch > codeNotEmpty( final String attributeName )
	{
		return ( ( root, query, criteriaBuilder ) -> criteriaBuilder.isNotNull( root.get( attributeName ) ) );
	}
}
