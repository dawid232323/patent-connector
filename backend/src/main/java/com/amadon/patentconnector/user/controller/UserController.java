package com.amadon.patentconnector.user.controller;

import com.amadon.patentconnector.shared.constants.AppEndpoints;
import com.amadon.patentconnector.user.service.UserRegistrationService;
import com.amadon.patentconnector.user.service.dto.CreateResearchInstitutionWorkerDto;
import com.amadon.patentconnector.user.service.dto.CreateUserDto;
import com.amadon.patentconnector.user.service.dto.SetPasswordDto;
import com.amadon.patentconnector.user.service.dto.UserDto;
import com.amadon.patentconnector.user.service.registrationPerformer.RegistrationType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping( value = AppEndpoints.UserEndpoints.userBase,
		produces = MediaType.APPLICATION_JSON_VALUE,
		consumes = MediaType.APPLICATION_JSON_VALUE )
public class UserController
{
	private final UserRegistrationService registrationService;

	@ResponseBody
	@PostMapping( AppEndpoints.UserEndpoints.entrepreneurRegister )
	@ResponseStatus( HttpStatus.CREATED )
	public UserDto registerEntrepreneur( @Valid @RequestBody final CreateUserDto aCreateUserDto )
	{
		return registrationService.registerUser( aCreateUserDto, RegistrationType.BASIC_ENTREPRENEUR );
	}

	@ResponseBody
	@PostMapping( AppEndpoints.UserEndpoints.researchInstitutionRegister )
	@ResponseStatus( HttpStatus.CREATED )
	public UserDto registerResearchInstitutionWorker( @Valid @RequestBody final CreateResearchInstitutionWorkerDto aCreateDto )
	{
		return registrationService.registerUser( aCreateDto, RegistrationType.BASIC_RESEARCH_INSTITUTION );
	}

	@ResponseStatus( HttpStatus.CREATED )
	@PostMapping( AppEndpoints.UserEndpoints.setInitialPassword )
	public void setUserInitialPassword( @Valid @RequestBody final SetPasswordDto aInitialPassword )
	{
		registrationService.setUserInitialPassword( aInitialPassword );
	}
}
