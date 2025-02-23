package com.amadon.patentconnector.businessBranch.service;

import com.amadon.patentconnector.businessBranch.entity.BusinessBranch;
import com.amadon.patentconnector.businessBranch.entity.BusinessBranchType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
final class BusinessBranchTypeResolver
{
	public BusinessBranchType resolveBranchType( final BusinessBranch businessBranch )
	{
		if ( isSectionBranch( businessBranch ) )
		{
			return BusinessBranchType.SECTION;
		}
		if ( isDepartmentBranch( businessBranch ) )
		{
			return BusinessBranchType.DEPARTMENT;
		}
		if ( isGroupBranch( businessBranch ) )
		{
			return BusinessBranchType.GROUP;
		}
		if ( isClassBranch( businessBranch ) )
		{
			return BusinessBranchType.CLASS;
		}
		return BusinessBranchType.STANDARD;
	}

	private boolean isSectionBranch( final BusinessBranch businessBranch )
	{
		return !isNull( businessBranch.getSection() )
				&& isNull( businessBranch.getDepartment() )
				&& isNull( businessBranch.getBusinessBranchGroup() )
				&& isNull( businessBranch.getBusinessBranchClass() );
	}

	private boolean isDepartmentBranch( final BusinessBranch businessBranch )
	{
		return !isNull( businessBranch.getSection() )
				&& !isNull( businessBranch.getDepartment() )
				&& isNull( businessBranch.getBusinessBranchGroup() )
				&& isNull( businessBranch.getBusinessBranchClass() );
	}

	private boolean isGroupBranch( final BusinessBranch businessBranch )
	{
		return !isNull( businessBranch.getSection() )
				&& !isNull( businessBranch.getDepartment() )
				&& !isNull( businessBranch.getBusinessBranchGroup() )
				&& isNull( businessBranch.getBusinessBranchClass() );
	}

	private boolean isClassBranch( final BusinessBranch businessBranch )
	{
		return !isNull( businessBranch.getSection() )
				&& !isNull( businessBranch.getDepartment() )
				&& !isNull( businessBranch.getBusinessBranchGroup() )
				&& !isNull( businessBranch.getBusinessBranchClass() );
	}

	private boolean isNull( final String value )
	{
		return Objects.isNull( value );
	}
}
