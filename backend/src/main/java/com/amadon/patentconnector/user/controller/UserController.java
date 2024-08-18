package com.amadon.patentconnector.user.controller;

import com.amadon.patentconnector.shared.constants.AppEndpoints;
import com.amadon.patentconnector.user.service.UserRegistrationService;
import com.amadon.patentconnector.user.service.dto.CreateUserDto;
import com.amadon.patentconnector.user.service.registrationPerformer.RegistrationType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping( AppEndpoints.UserEndpoints.userBase )
public class UserController
{
	private final UserRegistrationService registrationService;

	@PostMapping( AppEndpoints.UserEndpoints.entrepreneurRegister )
	@ResponseStatus( HttpStatus.CREATED )
	public void	registerEntrepreneur( @RequestBody final CreateUserDto aCreateUserDto )
	{
		registrationService.registerUser( aCreateUserDto, RegistrationType.BASIC_ENTREPRENEUR );
	}
}
