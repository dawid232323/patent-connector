package com.amadon.patentconnector.user.service.validator;

import com.amadon.patentconnector.user.service.dto.CreateUserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class EntrepreneurValidationRuleEngine
{
	private final List< CommonUserRegistrationValidatorRule > commonValidators;
	private final List< CreateEntrepreneurUserValidationRule > entrepreneurValidators;

	public void validate( final CreateUserDto aCreateUserDto )
	{
		log.info( "Validating user create dto for {}", aCreateUserDto.getEmail() );
		commonValidators.forEach( validator -> validator.validate( aCreateUserDto ) );
		entrepreneurValidators.forEach( validator -> validator.validate( aCreateUserDto ) );
	}
}
