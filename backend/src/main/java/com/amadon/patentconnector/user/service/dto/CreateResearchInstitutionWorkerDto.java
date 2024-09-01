package com.amadon.patentconnector.user.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static com.amadon.patentconnector.shared.constants.ValidationMessages.CommonMessages.*;
import static com.amadon.patentconnector.shared.constants.ValidationMessages.StringLengthMessages.MAX_300_CHARACTERS;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateResearchInstitutionWorkerDto implements CreateUser
{
	@Email( message = EMAIL )
	private String email;

	@NotBlank( message = NOT_BLANK )
	@Size( max = 300, message = MAX_300_CHARACTERS )
	private String firstName;

	@NotBlank( message = NOT_BLANK )
	@Size( max = 300, message = MAX_300_CHARACTERS )
	private String lastName;

	@NotNull( message = NOT_NULL )
	private Long institutionId;
}
