package com.amadon.patentconnector.user.service.dto;

import com.amadon.patentconnector.researchInstitution.service.dto.ResearchInstitutionDto;
import com.amadon.patentconnector.user.entity.UserRole;
import com.amadon.patentconnector.user.features.entrepreneurData.service.dto.CreateEntrepreneursDataDto;
import com.amadon.patentconnector.user.features.entrepreneurData.service.dto.EntrepreneursDataDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.util.Collection;

/**
 * DTO for {@link com.amadon.patentconnector.user.entity.User}
 */
@AllArgsConstructor
@Getter
public class UserDto implements Serializable
{
	private final Long id;
	private final String email;
	private final String firstName;
	private final String lastName;
	private final Boolean isActive;
	private final EntrepreneursDataDto entrepreneursData;
	private final ResearchInstitutionDto researchInstitution;
	private final Collection< UserRole > roles;
}