package com.amadon.patentconnector.comment.service.validation.rules;

import com.amadon.patentconnector.comment.entity.CommentType;
import com.amadon.patentconnector.comment.service.dto.CreateCommentDto;
import com.amadon.patentconnector.comment.service.exception.CommentValidationException;
import com.amadon.patentconnector.comment.service.validation.CommentValidationRule;
import com.amadon.patentconnector.patent.service.PatentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class PatentExistsValidationRule implements CommentValidationRule
{
	private final PatentRepository patentRepository;

	@Override
	public void validateCreate( final CreateCommentDto aCreateCommentDto )
	{
		if ( Objects.isNull( aCreateCommentDto.getPatentId() ) )
		{
			throw new CommentValidationException( "Patent id cannot be null" );
		}
		if ( !patentRepository.existsById( aCreateCommentDto.getPatentId() ) )
		{
			throw new CommentValidationException( "Patent does not exist" );
		}
	}

	@Override
	public boolean isApplicable( final CommentType aCommentType )
	{
		return aCommentType.equals( CommentType.PATENT );
	}
}
