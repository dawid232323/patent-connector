package com.amadon.patentconnector.patent.service.componentCreator.impl;

import com.amadon.patentconnector.businessBranch.entity.BusinessBranch;
import com.amadon.patentconnector.businessBranch.service.BusinessBranchService;
import com.amadon.patentconnector.patent.entity.PatentAnalysisDatum;
import com.amadon.patentconnector.patent.entity.PatentUsageDescription;
import com.amadon.patentconnector.patent.service.componentCreator.PatentComponentCreator;
import com.amadon.patentconnector.patent.service.dto.create.CreatePatentAnalysisDatumDto;
import com.amadon.patentconnector.patent.service.dto.create.CreatePatentUsageDescriptionDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

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
		final Set< PatentUsageDescription > usageDescriptions = getPatentUsageDescriptions( aCreateComponentDto.getUsageDescriptions() );
		final PatentAnalysisDatum analysisDatum = PatentAnalysisDatum.builder()
				.businessBranches( branches )
				.build();
		usageDescriptions.forEach( usage -> usage.setPatentAnalysis( analysisDatum ) );
		analysisDatum.setPatentUsageDescriptions( usageDescriptions );
		return analysisDatum;
	}

	private Set< BusinessBranch > getBranchesForPatentAnalysis( final CreatePatentAnalysisDatumDto aDto )
	{
		final List< String > codes = new ArrayList<>( aDto.getBusinessBranchesCodes() );
		return new HashSet<>( businessBranchService.getBusinessBranchesForCodes( codes ) );
	}

	private Map< String, BusinessBranch > getBranchesForUsageDescriptions( final Set< CreatePatentUsageDescriptionDto > aDtos )
	{
		final List< String > usageDescriptionBBCodes = aDtos.stream().map( CreatePatentUsageDescriptionDto::getBranchCode ).toList();
		return businessBranchService
				.getBusinessBranchesForCodes( usageDescriptionBBCodes )
				.stream().collect( Collectors.toMap( b -> b.getCode().trim(), Function.identity() ) );
	}

	private Set< PatentUsageDescription > getPatentUsageDescriptions( final Set< CreatePatentUsageDescriptionDto > aDtos )
	{
		final Map< String, BusinessBranch > branches = getBranchesForUsageDescriptions( aDtos );
		final Set< PatentUsageDescription > patentUsageDescriptions = new HashSet<>();
		aDtos.forEach( usage -> {
			patentUsageDescriptions.add( createUsageDescription( usage, branches.get( usage.getBranchCode() ) ) );
		});
		return patentUsageDescriptions;
	}

	private PatentUsageDescription createUsageDescription( final CreatePatentUsageDescriptionDto aDto, final BusinessBranch aBranch )
	{
		final PatentUsageDescription usageDescription = PatentUsageDescription.builder()
				.businessBranch( aBranch )
				.build();
		usageDescription.setDescriptions( aDto.getDescriptions() );
		return usageDescription;
	}
}
