package com.amadon.patentconnector.patent.service;

import com.amadon.patentconnector.patent.entity.Patent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PatentRepository extends JpaRepository< Patent, Long >, JpaSpecificationExecutor< Patent >
{
	@Query( "select p from Patent p " +
			"right join fetch p.bibliographicData pb " +
			"right join fetch p.searchReportData ps " +
			"left join fetch ps.patentCitations " +
			"where p.id = ?1" )
	Patent getPatent( Long id );
}
