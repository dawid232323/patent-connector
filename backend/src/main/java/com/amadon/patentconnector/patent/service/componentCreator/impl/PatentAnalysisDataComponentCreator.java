package com.amadon.patentconnector.patent.service.componentCreator.impl;

import com.amadon.patentconnector.businessBranch.entity.BusinessBranch;
import com.amadon.patentconnector.businessBranch.service.BusinessBranchService;
import com.amadon.patentconnector.patent.entity.PatentAnalysisDatum;
import com.amadon.patentconnector.patent.service.componentCreator.PatentComponentCreator;
import com.amadon.patentconnector.patent.service.dto.create.CreatePatentAnalysisDatumDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class PatentAnalysisDataComponentCreator
		implements PatentComponentCreator< CreatePatentAnalysisDatumDto, PatentAnalysisDatum >
{
	private final BusinessBranchService businessBranchService;

	@Override
	public PatentAnalysisDatum resolvePatentComponent( final CreatePatentAnalysisDatumDto aCreateComponentDto )
	{
		final Set< BusinessBranch > branches = getBranchesForPatentAnalysis( aCreateComponentDto );
		return PatentAnalysisDatum.builder()
				.businessBranches( branches )
				.build();
	}

	private Set< BusinessBranch > getBranchesForPatentAnalysis( final CreatePatentAnalysisDatumDto aDto )
	{
		final List< String > codes = new ArrayList<>( aDto.getBusinessBranchesCodes() );
		return new HashSet<>( businessBranchService.getBusinessBranchesForCodes( codes ) );
	}
}
