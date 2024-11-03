package com.amadon.patentconnector.patent.service.specification;

import com.amadon.patentconnector.businessBranch.entity.BusinessBranch;
import com.amadon.patentconnector.patent.entity.Patent;
import com.amadon.patentconnector.patent.entity.PatentAnalysisDatum;
import com.amadon.patentconnector.patent.entity.PatentBibliographicDatum;
import com.amadon.patentconnector.patent.service.dto.PatentSearchQueryDto;
import com.amadon.patentconnector.shared.service.specification.SpecificationProvider;
import jakarta.annotation.Nullable;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class PatentSpecificationProvider implements SpecificationProvider< PatentSearchQueryDto, Patent >
{
	@Override
	public Specification< Patent > getSpecification( final PatentSearchQueryDto searchQuery )
	{
		Specification< Patent > baseSpecification = noSpec();
		if ( queryTitle( searchQuery.getTitle() ) )
		{
			baseSpecification = baseSpecification.and( withTitleLike( searchQuery.getTitle() ) );
		}
		if ( queryBusinessBranches( searchQuery.getBusinessBranchesIds() ) )
		{
			baseSpecification =
					baseSpecification.and( withBusinessBranchesIn( searchQuery.getBusinessBranchesIds() ) );
		}
		if ( queryDateCreated( searchQuery.getDateCreated() ) )
		{
			baseSpecification = baseSpecification.and( withDateCreated( searchQuery.getDateCreated() ) );
		}
		return baseSpecification;
	}

	private Specification< Patent > withTitleLike( final String aTitle )
	{
		return ( root, query, cb ) ->
		{
			final Join< Patent, PatentBibliographicDatum > bibJoin = root.join( "bibliographicData" );
			final Predicate bibDataTitle = cb.like( cb.lower( bibJoin.get( "inventionTitle" ) ),
													getSearchLikeString( aTitle ) );
			final Predicate baseTitleSpec = cb.like( cb.lower( root.get( "title" ) ), getSearchLikeString( aTitle ) );
			return cb.or( bibDataTitle, baseTitleSpec );
		};
	}

	private Specification< Patent > withBusinessBranchesIn( final List< Long > businessBranchesIds )
	{
		return ( root, query, cb ) -> {
			final Join< Patent, PatentAnalysisDatum > analysisDatumJoin = root.join( "patentAnalysisData" );
			final Join< PatentAnalysisDatum, BusinessBranch > businessBranchJoin = analysisDatumJoin.join( "businessBranches" );
			return businessBranchJoin.get( "id" ).in( businessBranchesIds );
		};
	}

	private Specification< Patent > withDateCreated( final LocalDate dateCreated )
	{
		return (root, query, cb ) -> cb.greaterThanOrEqualTo( root.get( "beginDate" ), dateCreated );
	}

	private boolean queryTitle( @Nullable final String aTitle )
	{
		return Objects.nonNull( aTitle ) && StringUtils.isNotBlank( aTitle );
	}

	private boolean queryBusinessBranches( final List< Long > businessBranchesIds )
	{
		return Objects.nonNull( businessBranchesIds ) && !businessBranchesIds.isEmpty();
	}

	private boolean queryDateCreated( final LocalDate dateCreated )
	{
		return Objects.nonNull( dateCreated );
	}
}
