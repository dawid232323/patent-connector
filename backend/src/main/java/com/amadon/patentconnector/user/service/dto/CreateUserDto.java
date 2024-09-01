package com.amadon.patentconnector.user.service.dto;

import com.amadon.patentconnector.user.features.entrepreneurData.service.dto.CreateEntrepreneursDataDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

import static com.amadon.patentconnector.shared.constants.ValidationMessages.CommonMessages.*;
import static com.amadon.patentconnector.shared.constants.ValidationMessages.StringLengthMessages.MAX_300_CHARACTERS;

/**
 * DTO for {@link com.amadon.patentconnector.user.entity.User}
 */
@AllArgsConstructor
@Getter
public class CreateUserDto implements Serializable, CreateUser
{
	@Email( message = EMAIL )
	private final String email;

	@NotBlank( message = NOT_BLANK )
	@Size( max = 300, message = MAX_300_CHARACTERS )
	private final String firstName;

	@NotBlank( message = NOT_BLANK )
	@Size( max = 300, message = MAX_300_CHARACTERS )
	private final String lastName;

	@NotNull( message = NOT_NULL )
	private final CreateEntrepreneursDataDto entrepreneursData;
}