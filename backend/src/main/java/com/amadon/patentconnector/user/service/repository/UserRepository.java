package com.amadon.patentconnector.user.service.repository;

import com.amadon.patentconnector.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserRepository extends JpaRepository< User, Long >
{
	Optional< User > findByEmail( String aEmail );

	@Query( "select u from User u where " +
			"u.researchInstitution is not null " +
			"and u.isActive = true " +
			"order by u.researchInstitution.name" )
	List< User > getInstitutionWorkers();

	@Query("select distinct u from User u " +
			"join fetch u.entrepreneursData ed " +
			"join fetch ed.interestedBusinessBranches bb " +
			"where u.isActive = true " +
			"and u.entrepreneursData.recommendationAgreement = true " +
			"and trim(bb.section) in :codes")
	List<User> getUsersToNotifyAboutNewPatents( @Param("codes") Set<String> sectionBBCodes );

}
