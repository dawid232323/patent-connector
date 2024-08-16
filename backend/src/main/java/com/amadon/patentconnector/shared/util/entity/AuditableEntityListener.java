package com.amadon.patentconnector.shared.util.entity;

import com.amadon.patentconnector.shared.entity.Auditable;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.LocalDateTime;

public class AuditableEntityListener
{
	// TODO when authenticating is introduced user data should be collected following this post
	//  https://stackoverflow.com/questions/12155632/injecting-a-spring-dependency-into-a-jpa-entitylistener

	@PrePersist
	void handlePrePersist( final Auditable aAuditable ) {
		aAuditable.setCreatedAt( LocalDateTime.now() );
	}

	@PreUpdate
	void handlePreUpdate( final Auditable aAuditable ) {
		aAuditable.setUpdatedAt( LocalDateTime.now() );
	}
}
