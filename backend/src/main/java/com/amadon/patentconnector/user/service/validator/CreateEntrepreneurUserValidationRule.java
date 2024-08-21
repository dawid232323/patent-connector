package com.amadon.patentconnector.user.service.validator;

import com.amadon.patentconnector.user.service.dto.CreateUserDto;

/**
 * Interface that validates entrepreneur user registration requests.
 */
public interface CreateEntrepreneurUserValidationRule extends UserRegisterValidationRule< CreateUserDto >
{
}
