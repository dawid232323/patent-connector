package com.amadon.patentconnector.security.service.permissionEvaluator.ipml;

import com.amadon.patentconnector.event.service.EventService;
import com.amadon.patentconnector.event.service.dto.EventDto;
import com.amadon.patentconnector.security.service.permissionEvaluator.EntityObjectType;
import com.amadon.patentconnector.security.service.permissionEvaluator.EntityTypePermissionEvaluatorStrategy;
import com.amadon.patentconnector.user.entity.User;
import com.amadon.patentconnector.user.entity.UserRole;
import com.amadon.patentconnector.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventPermissionEvaluator implements EntityTypePermissionEvaluatorStrategy
{
	private final UserService userService;
	private final EventService eventService;

	@Override
	public boolean hasPermissionForObject( final Long aEntityId, final Authentication aAuthentication )
	{
		log.info( "Checking if user {} has permission for event with id {}", aAuthentication.getPrincipal(),
				  aEntityId );
		if ( aAuthentication.getAuthorities()
				.contains( UserRole.ADMIN ) )
		{
			return true;
		}
		final User user = userService.tryToFindByEmail( String.valueOf( aAuthentication.getPrincipal() ) )
				.orElseThrow();
		final EventDto event = eventService.get( aEntityId );
		return event.getCaregiver()
				.getId()
				.equals( user.getId() );
	}

	@Override
	public boolean applicable( final EntityObjectType aObjectType )
	{
		return aObjectType.equals( EntityObjectType.EVENT );
	}
}
