package com.amadon.patentconnector.inventionDemand.controller;

import com.amadon.patentconnector.inventionDemand.service.InventionDemandService;
import com.amadon.patentconnector.inventionDemand.service.dto.InventionDemandDto;
import com.amadon.patentconnector.shared.constants.AppEndpoints;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping( value = AppEndpoints.InventionDemandEndpoints.inventionDemandBase,
		produces = MediaType.APPLICATION_JSON_VALUE,
		consumes = MediaType.APPLICATION_JSON_VALUE )
public class InventionDemandController
{
	private final InventionDemandService inventionDemandService;

	@PostMapping
	@PreAuthorize( "hasAuthority('ENTREPRENEUR')" )
	public ResponseEntity< ? > crateInventionDemand( @RequestBody final InventionDemandDto inventionDemand ) {
		inventionDemandService.createInventionDemand( inventionDemand );
		return new ResponseEntity<>( HttpStatus.CREATED );
	}
}
