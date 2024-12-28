package com.amadon.patentconnector.comment.service.validation.rules;

import com.amadon.patentconnector.comment.entity.Comment;
import com.amadon.patentconnector.comment.entity.CommentType;
import com.amadon.patentconnector.comment.service.dto.CreateCommentDto;
import com.amadon.patentconnector.comment.service.exception.CommentValidationException;
import com.amadon.patentconnector.comment.service.repository.CommentRepository;
import com.amadon.patentconnector.comment.service.validation.CommentValidationRule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ParentCommentValidationRule implements CommentValidationRule
{

	private final CommentRepository commentRepository;

	@Override
	public void validateCreate( final CreateCommentDto aCreateCommentDto )
	{
		if ( Objects.isNull( aCreateCommentDto.getParentId() ) )
		{
			return;
		}
		final Comment comment = commentRepository.findById( aCreateCommentDto.getParentId() )
				.orElseThrow( () -> new CommentValidationException( "Parent comment not found" ) );
		if ( Objects.nonNull( comment.getParent() ) )
		{
			throw new CommentValidationException( "Parent comment is replay to another comment" );
		}
	}

	@Override
	public boolean isApplicable( final CommentType aCommentType )
	{
		return true;
	}
}
