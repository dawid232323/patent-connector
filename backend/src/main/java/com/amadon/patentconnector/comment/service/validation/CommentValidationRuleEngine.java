package com.amadon.patentconnector.comment.service.validation;

import com.amadon.patentconnector.comment.entity.CommentType;
import com.amadon.patentconnector.comment.service.dto.CreateCommentDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class CommentValidationRuleEngine
{

	private final List< CommentValidationRule > validationRules;

	public void validateCreateComment( final CreateCommentDto aCreateCommentDto, final CommentType aCommentType )
	{
		validationRules.stream()
				.filter( rule -> rule.isApplicable( aCommentType ) )
				.forEach( rule -> rule.validateCreate( aCreateCommentDto ) );
	}
}
