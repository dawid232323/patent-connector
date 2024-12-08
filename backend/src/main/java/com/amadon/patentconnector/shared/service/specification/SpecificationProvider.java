package com.amadon.patentconnector.shared.service.specification;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.Objects;

/**
 * @param <T> object containing all available query params
 * @param <K> type of entity to be queried
 */
public interface SpecificationProvider<T, K>
{
	Specification< K > getSpecification( T searchQuery );

	default Specification< K > noSpec()
	{
		return ( ( root, query, criteriaBuilder ) -> criteriaBuilder.isNotNull( root.get( "id" ) ) );
	}

	default String getSearchLikeString( String aQueryValue )
	{
		if ( Objects.isNull( aQueryValue ) || StringUtils.isBlank( aQueryValue ) )
		{
			return "%";
		}
		return "%" + aQueryValue.toLowerCase().trim() + "%";
	}

	default boolean atrNotEmpty( final Object aAttribute )
	{
		return Objects.nonNull( aAttribute );
	}
}
