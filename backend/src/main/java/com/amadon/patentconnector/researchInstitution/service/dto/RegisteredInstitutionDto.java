package com.amadon.patentconnector.researchInstitution.service.dto;

import com.amadon.patentconnector.user.service.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisteredInstitutionDto
{
	private String institutionName;
	private String institutionEmail;
	private List< UserDto > workers;
}
