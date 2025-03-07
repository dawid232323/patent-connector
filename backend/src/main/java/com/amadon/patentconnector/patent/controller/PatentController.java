package com.amadon.patentconnector.patent.controller;

import com.amadon.patentconnector.patent.entity.Patent;
import com.amadon.patentconnector.patent.service.PatentService;
import com.amadon.patentconnector.patent.service.dto.PatentDto;
import com.amadon.patentconnector.patent.service.dto.PatentSearchQueryDto;
import com.amadon.patentconnector.patent.service.dto.PatentSearchResultDto;
import com.amadon.patentconnector.patent.service.dto.create.CreatePatentDto;
import com.amadon.patentconnector.patent.service.mapper.PatentMapper;
import com.amadon.patentconnector.security.service.SecurityService;
import com.amadon.patentconnector.shared.constants.AppEndpoints;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

	@ResponseBody
	@ResponseStatus( HttpStatus.CREATED )
	@PostMapping( AppEndpoints.PatentEndpoints.uploadPatents )
	public List< PatentDto > createPatents( @RequestHeader( name = "X-Admin-Key" ) final String aAdminKey,
											@RequestBody final List< CreatePatentDto > aCreatePatentDtos )
	{
		securityService.validateAdminKey( aAdminKey );
		return patentService.createPatents( aCreatePatentDtos );
	}

	@ResponseBody
	@GetMapping( AppEndpoints.PatentEndpoints.patentFind )
	public Page< PatentSearchResultDto > findPatents( @ModelAttribute final PatentSearchQueryDto aSearchQueryDto, final Pageable aPage )
	{
		return patentService.findPatents( aSearchQueryDto, aPage );
	}

	@ResponseBody
	@GetMapping( "/{patentId}" )
	public PatentDto getPatent( @PathVariable( "patentId" ) final Long patentId )
	{
		return patentService.getPatent( patentId );
	}
}
