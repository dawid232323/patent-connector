package com.amadon.patentconnector.researchInstitution.controller;

import com.amadon.patentconnector.researchInstitution.service.ResearchInstitutionService;
import com.amadon.patentconnector.researchInstitution.service.dto.ResearchInstitutionDto;
import com.amadon.patentconnector.shared.constants.AppEndpoints;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping( value = AppEndpoints.ResearchInstitutionEndpoints.resInstitutionBase, produces =
		MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE )
public class ResearchInstitutionController
{
	private final ResearchInstitutionService institutionService;

	@GetMapping( value = AppEndpoints.ResearchInstitutionEndpoints.resInstitutionFind + "/{email}" )
	public List< ResearchInstitutionDto > findResearchInstitution( @PathVariable( "email" ) final String aUserEmail )
	{
		return institutionService.findByMatchingEmails( aUserEmail );
	}
}
