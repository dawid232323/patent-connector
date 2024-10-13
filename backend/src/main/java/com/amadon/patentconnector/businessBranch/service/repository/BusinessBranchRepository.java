package com.amadon.patentconnector.businessBranch.service.repository;

import com.amadon.patentconnector.businessBranch.entity.BusinessBranch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BusinessBranchRepository extends JpaRepository< BusinessBranch, Long >,
		JpaSpecificationExecutor< BusinessBranch >
{
	@Query( "select busBran from BusinessBranch busBran where busBran.department is null " +
			"and busBran.businessBranchClass is null " +
			"and busBran.department is null " +
			"and busBran.code is null " +
			"and busBran.businessBranchGroup is null order by busBran.section, busBran.displayName" )
	List< BusinessBranch > resolveAllSectionBusinessBranches();

	@Query( "select busBran from BusinessBranch busbran where trim(busbran.code) in :branchCodes " )
	List< BusinessBranch > resolveAllBranchesByCodeIn( @Param( "branchCodes" ) List< String > aBranchCodes );

	List< BusinessBranch > findAllByIdIn( List< Long > aBusinessBranchesIds );
}