package com.amadon.patentconnector.security.service.permissionEvaluator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ObjectBasedPermissionEvaluator implements PermissionEvaluator
{

	private final List< EntityTypePermissionEvaluatorStrategy > permissionEvaluatorStrategies;

	@Override
	public boolean hasPermission( final Authentication authentication, final Object targetDomainObject,
								  final Object permission )
	{
		log.error( "Trying to check permissions in unsupported method" );
		throw new UnsupportedOperationException( "Not supported yet." );
	}

	@Override
	public boolean hasPermission( final Authentication authentication, final Serializable targetId,
								  final String targetType, final Object permission )
	{
		log.info( "Going to determine permission for object type {}, with id {}, for user {}", targetType, targetId, authentication.getPrincipal() );
		final EntityObjectType objectType = EntityObjectType.valueOf( targetType );
		final EntityTypePermissionEvaluatorStrategy strategy = resolveStrategy( objectType );
		return strategy.hasPermissionForObject( (Long) targetId, authentication );
	}

	private EntityTypePermissionEvaluatorStrategy resolveStrategy( final EntityObjectType aObjectType )
	{
		return permissionEvaluatorStrategies.stream()
				.filter( strategy -> strategy.applicable( aObjectType ) )
				.findFirst()
				.orElseThrow( () -> new IllegalArgumentException( "No permission strategy found for object type " + aObjectType ) );
	}
}
