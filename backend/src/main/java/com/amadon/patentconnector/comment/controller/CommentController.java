package com.amadon.patentconnector.comment.controller;

import com.amadon.patentconnector.comment.entity.CommentType;
import com.amadon.patentconnector.comment.service.CommentService;
import com.amadon.patentconnector.comment.service.dto.CommentDto;
import com.amadon.patentconnector.comment.service.dto.CreateCommentDto;
import com.amadon.patentconnector.shared.constants.AppEndpoints;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
import java.util.Optional;

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

	@GetMapping( AppEndpoints.CommentEndpoints.patentComments + "/{patentId}" )
	public Page< CommentDto> getPatentComments( @PathVariable( "patentId" ) final Long patentId, final Pageable aPageable )
	{
		return commentService.getObjectComments( patentId, aPageable, CommentType.PATENT );
	}

	@GetMapping( AppEndpoints.CommentEndpoints.eventComments + "/{eventId}" )
	public Page< CommentDto> getEventComments( @PathVariable( "eventId" ) final Long aEventId, final Pageable aPageable )
	{
		return commentService.getObjectComments( aEventId, aPageable, CommentType.EVENT );
	}

	@PutMapping( AppEndpoints.CommentEndpoints.patentComments + "/{commentId}" )
	@PreAuthorize( "hasAnyAuthority( 'RESEARCH_WORKER', 'ADMIN', 'ENTREPRENEUR' ) and hasPermission(#commentId, 'COMMENT', null)" )
	public CommentDto updatePatentComment( @PathVariable( "commentId" ) final Long commentId, @RequestBody Map< String, String > aCommentContent )
	{
		final String updatedContent = Optional.ofNullable( aCommentContent.getOrDefault( "content", null ) )
				.orElseThrow(IllegalArgumentException::new);
		return commentService.updateComment( updatedContent, commentId, CommentType.PATENT );
	}

	@PutMapping( AppEndpoints.CommentEndpoints.eventComments + "/{commentId}" )
	@PreAuthorize( "hasAnyAuthority( 'RESEARCH_WORKER', 'ADMIN', 'ENTREPRENEUR' ) and hasPermission(#commentId, 'COMMENT', null)" )
	public CommentDto updateEventComment( @PathVariable( "commentId" ) final Long commentId, @RequestBody Map< String, String > aCommentContent )
	{
		final String updatedContent = Optional.ofNullable( aCommentContent.getOrDefault( "content", null ) )
				.orElseThrow(IllegalArgumentException::new);
		return commentService.updateComment( updatedContent, commentId, CommentType.EVENT );
	}

	@DeleteMapping( AppEndpoints.CommentEndpoints.patentComments + "/{commentId}" )
	@PreAuthorize( "hasAnyAuthority( 'RESEARCH_WORKER', 'ADMIN', 'ENTREPRENEUR' ) and hasPermission(#aCommentId, 'COMMENT', null)" )
	public ResponseEntity< ? > deletePatentComment( @PathVariable( "commentId" ) final Long aCommentId )
	{
		commentService.deleteComment( aCommentId, CommentType.PATENT );
		return ResponseEntity.ok().build();
	}

	@DeleteMapping( AppEndpoints.CommentEndpoints.eventComments + "/{commentId}" )
	@PreAuthorize( "hasAnyAuthority( 'RESEARCH_WORKER', 'ADMIN', 'ENTREPRENEUR' ) and hasPermission(#commentId, 'COMMENT', null)" )
	public ResponseEntity< ? > deleteEventComment( @PathVariable( "commentId" ) final Long commentId )
	{
		commentService.deleteComment( commentId, CommentType.EVENT );
		return ResponseEntity.ok().build();
	}
}
