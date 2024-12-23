package com.amadon.patentconnector.user.service.repository;

import com.amadon.patentconnector.user.entity.User;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository< User, Long >
{
	Optional< User > findByEmail( String aEmail );

	@Query( "select u from User u where " +
			"u.researchInstitution is not null " +
			"and u.isActive = true " +
			"order by u.researchInstitution.name" )
	List< User > getInstitutionWorkers();
}
