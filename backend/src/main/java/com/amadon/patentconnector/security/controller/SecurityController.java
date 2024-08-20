package com.amadon.patentconnector.security.controller;

import com.amadon.patentconnector.security.service.SecurityService;
import com.amadon.patentconnector.security.service.dto.LoginDto;
import com.amadon.patentconnector.security.service.dto.TokenDto;
import com.amadon.patentconnector.shared.constants.AppEndpoints;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping( value = AppEndpoints.SecurityEndpoints.securityBase, consumes = MediaType.APPLICATION_JSON_VALUE,
		produces = MediaType.APPLICATION_JSON_VALUE )
public class SecurityController
{

	private final SecurityService securityService;

	@ResponseBody
	@PostMapping( AppEndpoints.SecurityEndpoints.login )
	public TokenDto login( @RequestBody final LoginDto aLoginDto )
	{
		return securityService.loginUser( aLoginDto );
	}
}
