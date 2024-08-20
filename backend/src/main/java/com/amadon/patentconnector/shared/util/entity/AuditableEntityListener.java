package com.amadon.patentconnector.shared.util.entity;

import com.amadon.patentconnector.security.service.SecurityService;
import com.amadon.patentconnector.shared.entity.Auditable;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AuditableEntityListener
{

	@Autowired
	private SecurityService securityService;

	@PrePersist
	void handlePrePersist( final Auditable aAuditable ) {
		aAuditable.setCreatedAt( LocalDateTime.now() );
		if ( securityService.isUserAuthenticated() )
		{
			aAuditable.setCreatedBy( securityService.getAuthenticatedUserEmail() );
		}
	}

	@PreUpdate
	void handlePreUpdate( final Auditable aAuditable ) {
		aAuditable.setUpdatedAt( LocalDateTime.now() );
		if ( securityService.isUserAuthenticated() )
		{
			aAuditable.setUpdatedBy( securityService.getAuthenticatedUserEmail() );
		}
	}
}
