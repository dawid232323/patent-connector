package com.amadon.patentconnector.shared.util.entity;

import com.amadon.patentconnector.shared.entity.Auditable;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

// FIXME add proper auditing with usernames

@Component
public class AuditableEntityListener
{
	@PrePersist
	void handlePrePersist( final Auditable aAuditable ) {

		aAuditable.setCreatedAt( LocalDateTime.now() );
	}

	@PreUpdate
	void handlePreUpdate( final Auditable aAuditable ) {
		aAuditable.setUpdatedAt( LocalDateTime.now() );
	}
}
