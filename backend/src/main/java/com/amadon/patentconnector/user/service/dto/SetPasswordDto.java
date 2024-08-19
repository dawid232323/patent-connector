package com.amadon.patentconnector.user.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

import static com.amadon.patentconnector.shared.constants.ValidationMessages.CommonMessages.PASSWORD;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SetPasswordDto
{
	@Pattern( regexp = "^(?=.*[A-Z])(?=.*\\d)[A-Za-z0-9]{8,}$", message = PASSWORD )
	private String password;
	private String validationToken;
}
