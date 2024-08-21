package com.amadon.patentconnector.researchInstitution.service;

import com.amadon.patentconnector.researchInstitution.entity.ResearchInstitution;
import com.amadon.patentconnector.researchInstitution.service.dto.ResearchInstitutionDto;
import com.amadon.patentconnector.researchInstitution.service.exception.ResearchInstitutionNotFoundException;
import com.amadon.patentconnector.researchInstitution.service.mapper.ResearchInstitutionMapper;
import com.amadon.patentconnector.researchInstitution.service.repository.ResearchInstitutionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResearchInstitutionService
{
	private final ResearchInstitutionRepository repository;
	private final ResearchInstitutionMapper mapper;

	public List< ResearchInstitutionDto > findByMatchingEmails( final String aUserEmail )
	{
		return repository.findByMatchingEmails( aUserEmail )
				.stream()
				.map( mapper::fromEntity )
				.collect( Collectors.toList() );
	}

	public ResearchInstitution getById( final Long aInstitutionId )
	{
		return repository.findById( aInstitutionId )
				.orElseThrow( ResearchInstitutionNotFoundException::new );
	}
}
