package com.amadon.patentconnector.user.service.registrationPerformer.impl;

import com.amadon.patentconnector.researchInstitution.entity.ResearchInstitution;
import com.amadon.patentconnector.researchInstitution.service.ResearchInstitutionService;
import com.amadon.patentconnector.user.entity.User;
import com.amadon.patentconnector.user.entity.UserRole;
import com.amadon.patentconnector.user.service.dto.CreateResearchInstitutionWorkerDto;
import com.amadon.patentconnector.user.service.mapper.UserMapper;
import com.amadon.patentconnector.user.service.registrationPerformer.RegistrationPerformer;
import com.amadon.patentconnector.user.service.registrationPerformer.RegistrationType;
import com.amadon.patentconnector.user.service.validator.InstitutionWorkerRegisterValidationRuleEngine;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ResearchInstitutionWorkerRegistrationPerformer
		implements RegistrationPerformer< CreateResearchInstitutionWorkerDto >
{
	private final InstitutionWorkerRegisterValidationRuleEngine validationRuleEngine;
	private final ResearchInstitutionService institutionService;
	private final UserMapper userMapper;

	@Override
	public User resolveUserToRegister( final CreateResearchInstitutionWorkerDto aCreateUserDto )
	{
		validationRuleEngine.validate( aCreateUserDto );

		final ResearchInstitution selectedInstitution =
				institutionService.getById( aCreateUserDto.getInstitutionId() );
		final User baseUser = userMapper.fromCreateInstitutionWorker( aCreateUserDto );

		baseUser.setResearchInstitution( selectedInstitution );
		baseUser.setAuthorities( UserRole.RESEARCH_WORKER );
		selectedInstitution.getUsers().add( baseUser );
		return baseUser;
	}

	@Override
	public boolean isApplicable( final RegistrationType aRegistrationType )
	{
		return aRegistrationType.equals( RegistrationType.BASIC_RESEARCH_INSTITUTION );
	}
}
