package com.amadon.patentconnector.event.service.specification;

import com.amadon.patentconnector.businessBranch.entity.BusinessBranch;
import com.amadon.patentconnector.event.entity.Event;
import com.amadon.patentconnector.event.service.dto.EventSearchQueryDto;
import com.amadon.patentconnector.shared.service.specification.SpecificationProvider;
import jakarta.persistence.criteria.Join;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventSpecificationProvider implements SpecificationProvider< EventSearchQueryDto, Event >
{
	@Override
	public Specification< Event > getSpecification( final EventSearchQueryDto searchQuery )
	{
		Specification< Event > specification = noSpec();
		if ( strNotBlank( searchQuery.getTitle() ) )
		{
			specification = specification.and( withTitleLike( searchQuery.getTitle() ) );
		}
		if ( atrNotEmpty( searchQuery.getDateFrom() ) )
		{
			specification = specification.and( withDateFrom( searchQuery.getDateFrom() ) );
		} else {
			specification = specification.and( withFutureDate() );
		}
		if ( atrNotEmpty( searchQuery.getDateTo() ) )
		{
			specification = specification.and( withEndDateTo( searchQuery.getDateTo() ) );
		}
		if ( atrNotEmpty( searchQuery.getSectionBranchesCodes() ) && !searchQuery.getSectionBranchesCodes()
				.isEmpty() )
		{
			specification = specification.and( withBusinessBranchesIn( searchQuery.getSectionBranchesCodes() ) );
		}
		return specification;
	}

	@Override
	public Specification< Event > noSpec()
	{
		return (( root, query, criteriaBuilder ) -> criteriaBuilder.isNull( root.get( "deletedOn" ) ));
	}

	private Specification< Event > withTitleLike( final String aTitle )
	{
		return ( root, query, cb ) -> cb.like( cb.trim( cb.lower( root.get( "title" ) ) ), getSearchLikeString( aTitle ) );
	}

	private Specification< Event > withFutureDate()
	{
		return ( ( root, query, criteriaBuilder ) -> criteriaBuilder.greaterThan( root.get( "startDate" ), LocalDateTime.now() ) );
	}

	private Specification< Event > withDateFrom( final LocalDateTime aDate )
	{
		if ( aDate.isBefore( LocalDateTime.now() ) ) {
			log.warn( "Provided date {} was smalles than current date. Defaulting to now.", aDate );
			return withFutureDate();
		}
		return ( ( root, query, criteriaBuilder ) -> criteriaBuilder.greaterThan( root.get( "startDate" ), aDate ) );
	}

	private Specification< Event > withEndDateTo( final LocalDateTime aEndDate )
	{
		return ( ( root, query, criteriaBuilder ) -> criteriaBuilder.between( root.get( "endDate" ),
																			  LocalDateTime.now(),
																			  aEndDate ) );
	}

	private Specification< Event > withBusinessBranchesIn( final List< String > aBusinessBranchesIds )
	{
		return ( ( root, query, criteriaBuilder ) ->
		{
			final Join< Event, BusinessBranch > bbJoin = root.join( "businessBranches" );
			return criteriaBuilder.trim( bbJoin.get( "section" ) )
					.in( aBusinessBranchesIds );
		} );
	}
}
