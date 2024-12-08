package com.amadon.patentconnector.event.service.repository;

import com.amadon.patentconnector.event.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository< Event, Long >, JpaSpecificationExecutor< Event >
{
	@Override
	@Query( "select e from Event e where e.id = :id and e.deletedOn is null " )
	Optional< Event > findById( @Param( "id" ) Long aId );
}