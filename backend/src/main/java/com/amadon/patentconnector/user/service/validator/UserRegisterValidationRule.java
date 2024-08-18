package com.amadon.patentconnector.user.service.validator;

import com.amadon.patentconnector.user.service.dto.CreateUserDto;

/**
 * Interface representing a validation rule for user registration.
 * This interface defines a contract for implementing classes to validate
 * user registration data encapsulated in a {@link CreateUserDto} object.
 */
public interface UserRegisterValidationRule {

	/**
	 * Validates the provided user creation data.
	 *
	 * @param aCreateUserDto the data transfer object containing user registration information to validate.
	 * @throws UserRegistrationValidationException if the validation fails.
	 */
	void validate( CreateUserDto aCreateUserDto );
}
