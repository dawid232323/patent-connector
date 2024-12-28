package com.amadon.patentconnector.comment.controller;

import com.amadon.patentconnector.comment.entity.CommentType;
import com.amadon.patentconnector.comment.service.CommentService;
import com.amadon.patentconnector.comment.service.dto.CommentDto;
import com.amadon.patentconnector.comment.service.dto.CreateCommentDto;
import com.amadon.patentconnector.shared.constants.AppEndpoints;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping( value = AppEndpoints.CommentEndpoints.commentBase,
		produces = MediaType.APPLICATION_JSON_VALUE,
		consumes = MediaType.APPLICATION_JSON_VALUE )
public class CommentController
{
	private final CommentService commentService;

	@ResponseStatus( HttpStatus.CREATED )
	@PostMapping( AppEndpoints.CommentEndpoints.patentComments )
	@PreAuthorize( "hasAnyAuthority( 'RESEARCH_WORKER', 'ADMIN', 'ENTREPRENEUR' )" )
	public CommentDto createPatentComment( @Valid @RequestBody CreateCommentDto commentDto )
	{
		return commentService.createComment( commentDto, CommentType.PATENT );
	}

	@ResponseStatus( HttpStatus.CREATED )
	@PostMapping( AppEndpoints.CommentEndpoints.eventComments )
	@PreAuthorize( "hasAnyAuthority( 'RESEARCH_WORKER', 'ADMIN', 'ENTREPRENEUR' )" )
	public CommentDto createEventComment( @Valid @RequestBody CreateCommentDto commentDto )
	{
		return commentService.createComment( commentDto, CommentType.EVENT );
	}
}
