package com.amadon.patentconnector.user.service.validator;

import com.amadon.patentconnector.user.service.dto.CreateResearchInstitutionWorkerDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class InstitutionWorkerRegisterValidationRuleEngine
{

	private final List< CommonUserRegistrationValidatorRule > commonValidators;
	private final List< CreateResearchInstitutionWorkerValidationRule > institutionValidators;

	public void validate( final CreateResearchInstitutionWorkerDto aInstitutionWorkerDto )
	{
		log.info( "Validating create institution worker dto for {}", aInstitutionWorkerDto.getEmail() );
		commonValidators.forEach( validator -> validator.validate( aInstitutionWorkerDto ) );
		institutionValidators.forEach( validator -> validator.validate( aInstitutionWorkerDto ) );
	}
}
