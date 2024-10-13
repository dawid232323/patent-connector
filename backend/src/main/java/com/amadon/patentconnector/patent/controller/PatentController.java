package com.amadon.patentconnector.patent.controller;

import com.amadon.patentconnector.patent.service.PatentService;
import com.amadon.patentconnector.patent.service.dto.create.CreatePatentDto;
import com.amadon.patentconnector.patent.service.mapper.PatentMapper;
import com.amadon.patentconnector.security.service.SecurityService;
import com.amadon.patentconnector.shared.constants.AppEndpoints;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping( value = AppEndpoints.PatentEndpoints.patentBase,
		produces = MediaType.APPLICATION_JSON_VALUE,
		consumes = MediaType.APPLICATION_JSON_VALUE )
public class PatentController
{
	private final PatentService patentService;
	private final SecurityService securityService;

	// TODO change return type to PatentDto when it is created
	@ResponseBody
	@ResponseStatus( HttpStatus.CREATED )
	@PostMapping( AppEndpoints.PatentEndpoints.uploadPatents )
	public List< Object > createPatents( @RequestHeader( name = "X-Admin-Key" ) final String aAdminKey,
										 @RequestBody final List< CreatePatentDto > aCreatePatentDtos )
	{
		securityService.validateAdminKey( aAdminKey );
		return patentService.createPatents( aCreatePatentDtos );
	}
}
