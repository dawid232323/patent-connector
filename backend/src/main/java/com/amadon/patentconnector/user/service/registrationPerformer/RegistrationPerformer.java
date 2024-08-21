package com.amadon.patentconnector.user.service.registrationPerformer;

import com.amadon.patentconnector.user.entity.User;
import com.amadon.patentconnector.user.service.dto.CreateUser;
import com.amadon.patentconnector.user.service.dto.CreateUserDto;

/**
 * Interface responsible for handling user registration processes.
 * Provides methods to resolve a user based on registration data and to check
 * if a specific registration type is applicable.
 */
public interface RegistrationPerformer<T extends CreateUser > {

	/**
	 * Resolves a user to be registered based on the provided user creation data.
	 *
	 * @param aCreateUserDto the data transfer object containing user creation information.
	 * @return the resolved {@link User} object ready for registration.
	 */
	User resolveUserToRegister( T aCreateUserDto);

	/**
	 * Checks if the specified registration type is applicable for the current context.
	 *
	 * @param aRegistrationType the type of registration to check.
	 * @return true if the registration type is applicable; false otherwise.
	 */
	boolean isApplicable(RegistrationType aRegistrationType);
}
