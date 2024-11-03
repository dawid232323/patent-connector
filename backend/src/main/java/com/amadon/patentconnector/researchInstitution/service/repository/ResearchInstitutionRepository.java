package com.amadon.patentconnector.researchInstitution.service.repository;

import com.amadon.patentconnector.researchInstitution.entity.ResearchInstitution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResearchInstitutionRepository extends JpaRepository< ResearchInstitution, Long >
{
	Optional< ResearchInstitution > findById( Long id );

	@Query( "select ri from ResearchInstitution ri where SUBSTRING(ri.email, LOCATE('@', ri.email) + 1) = SUBSTRING(:email, LOCATE('@', :email) + 1)" )
	List< ResearchInstitution > findByMatchingEmails( @Param( "email" ) String aUserEmail );

	@Query( "select ri from ResearchInstitution ri left join fetch ri.users where ri.users.size > 0" )
	List< ResearchInstitution > findRegistered();
}
