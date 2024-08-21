package com.amadon.patentconnector.user.service.validator.impl;

import com.amadon.patentconnector.researchInstitution.service.ResearchInstitutionService;
import com.amadon.patentconnector.user.service.dto.CreateResearchInstitutionWorkerDto;
import com.amadon.patentconnector.user.service.exception.UserRegistrationException;
import com.amadon.patentconnector.user.service.validator.CreateResearchInstitutionWorkerValidationRule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class InstitutionalAffiliationValidationRule implements CreateResearchInstitutionWorkerValidationRule
{
	private final ResearchInstitutionService institutionService;

	@Override
	public void validate( final CreateResearchInstitutionWorkerDto aCreateUserDto )
	{
		if ( !isCorrectInstitutionSelected( aCreateUserDto ) )
		{
			throw new UserRegistrationException( "Selected institution does not match user email" );
		}
	}

	private boolean isCorrectInstitutionSelected( final CreateResearchInstitutionWorkerDto aInstitutionWorkerDto )
	{
		return institutionService.findByMatchingEmails( aInstitutionWorkerDto.getEmail() )
				.stream()
				.anyMatch( aInstitution -> aInstitution.getId().equals( aInstitutionWorkerDto.getInstitutionId() ) );
	}
}
