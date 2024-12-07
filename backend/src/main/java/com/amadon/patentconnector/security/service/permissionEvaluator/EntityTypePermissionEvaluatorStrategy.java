package com.amadon.patentconnector.security.service.permissionEvaluator;

import org.springframework.security.core.Authentication;

public interface EntityTypePermissionEvaluatorStrategy
{
	boolean hasPermissionForObject( Long aEntityId, Authentication aAuthentication );

	boolean applicable( EntityObjectType aObjectType );
}
