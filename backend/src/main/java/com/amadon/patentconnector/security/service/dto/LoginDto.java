package com.amadon.patentconnector.security.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

import static com.amadon.patentconnector.shared.constants.ValidationMessages.CommonMessages.NOT_BLANK;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto
{
	@NotBlank( message = NOT_BLANK )
	private String email;

	@NotBlank( message = NOT_BLANK )
	private String password;
}
