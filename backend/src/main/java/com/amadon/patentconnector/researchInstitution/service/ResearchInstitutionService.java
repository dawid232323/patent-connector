package com.amadon.patentconnector.researchInstitution.service;

import com.amadon.patentconnector.researchInstitution.entity.ResearchInstitution;
import com.amadon.patentconnector.researchInstitution.service.dto.RegisteredInstitutionDto;
import com.amadon.patentconnector.researchInstitution.service.dto.ResearchInstitutionDto;
import com.amadon.patentconnector.researchInstitution.service.exception.ResearchInstitutionNotFoundException;
import com.amadon.patentconnector.researchInstitution.service.mapper.ResearchInstitutionMapper;
import com.amadon.patentconnector.researchInstitution.service.repository.ResearchInstitutionRepository;
import com.amadon.patentconnector.user.entity.User;
import com.amadon.patentconnector.user.service.UserService;
import com.amadon.patentconnector.user.service.dto.UserDto;
import com.amadon.patentconnector.user.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResearchInstitutionService
{
	private final ResearchInstitutionRepository repository;
	private final ResearchInstitutionMapper mapper;
	private final UserMapper userMapper;

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

	public List< RegisteredInstitutionDto > findRegistered()
	{
		return repository.findRegistered()
				.stream()
				.peek( institution ->
							   institution.setUsers( getInstitutionActiveWorkers( institution ) ) )
				.filter( institution -> !institution.getUsers()
						.isEmpty() )
				.map( this::map )
				.toList();
	}

	private Set< User > getInstitutionActiveWorkers( final ResearchInstitution researchInstitution )
	{
		return researchInstitution.getUsers()
				.stream()
				.filter( User::getIsActive )
				.collect( Collectors.toSet() );
	}

	private RegisteredInstitutionDto map( final ResearchInstitution researchInstitution )
	{
		final List< UserDto > workers = researchInstitution.getUsers()
				.stream()
				.map( userMapper::toDto )
				.toList();
		return RegisteredInstitutionDto.builder()
				.institutionName( researchInstitution.getName() )
				.institutionEmail( researchInstitution.getEmail() )
				.workers( workers )
				.build();
	}
}
