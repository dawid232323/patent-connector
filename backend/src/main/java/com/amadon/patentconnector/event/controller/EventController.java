package com.amadon.patentconnector.event.controller;

import com.amadon.patentconnector.event.service.EventService;
import com.amadon.patentconnector.event.service.dto.EventDto;
import com.amadon.patentconnector.event.service.dto.PersistEventDto;
import com.amadon.patentconnector.shared.constants.AppEndpoints;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping( value = AppEndpoints.EventEndpoints.eventBase,
		produces = MediaType.APPLICATION_JSON_VALUE,
		consumes = MediaType.APPLICATION_JSON_VALUE )
public class EventController
{
	private final EventService eventService;

	// TODO add security
	@PostMapping
	@ResponseBody
	@ResponseStatus( HttpStatus.CREATED )
	@PreAuthorize( "hasAnyAuthority( 'RESEARCH_WORKER', 'ADMIN' )" )
	public EventDto createEvent( @Valid @RequestBody PersistEventDto eventDto )
	{
		return eventService.create( eventDto );
	}

	// TODO add object based security
	@ResponseBody
	@PutMapping( "/{eventId}" )
	@ResponseStatus( HttpStatus.OK )
	@PreAuthorize( "hasAnyAuthority( 'RESEARCH_WORKER', 'ADMIN' ) and hasPermission(#aEventId, 'EVENT', null)" )
	public EventDto updateEvent( @PathVariable( "eventId" ) final Long aEventId,
								 @Valid @RequestBody PersistEventDto eventDto )
	{
		return eventService.update( eventDto, aEventId );
	}

	@ResponseBody
	@GetMapping( "/{eventId}" )
	@ResponseStatus( HttpStatus.OK )
	public EventDto getEvent( @PathVariable( "eventId" ) final Long aEventId )
	{
		return eventService.get( aEventId );
	}
}
