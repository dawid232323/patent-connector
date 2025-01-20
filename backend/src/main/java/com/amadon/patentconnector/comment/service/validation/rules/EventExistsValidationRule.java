package com.amadon.patentconnector.comment.service.validation.rules;

import com.amadon.patentconnector.comment.entity.CommentType;
import com.amadon.patentconnector.comment.service.dto.CreateCommentDto;
import com.amadon.patentconnector.comment.service.exception.CommentValidationException;
import com.amadon.patentconnector.comment.service.validation.CommentValidationRule;
import com.amadon.patentconnector.event.service.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class EventExistsValidationRule implements CommentValidationRule
{

	private final EventRepository eventRepository;

	@Override
	public void validateCreate( final CreateCommentDto aCreateCommentDto )
	{
		if ( Objects.isNull( aCreateCommentDto.getEventId() ) )
		{
			throw new CommentValidationException( "Patent id cannot be null" );
		}
		if ( !eventRepository.existsById( aCreateCommentDto.getEventId() ) )
		{
			throw new CommentValidationException( "Event does not exist" );
		}
	}

	@Override
	public boolean isApplicable( final CommentType aCommentType )
	{
		return aCommentType.equals( CommentType.EVENT );
	}
}
