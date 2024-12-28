package com.amadon.patentconnector.comment.service.validation;

import com.amadon.patentconnector.comment.entity.CommentType;
import com.amadon.patentconnector.comment.service.dto.CreateCommentDto;

public interface CommentValidationRule
{
	void validateCreate( CreateCommentDto aCreateCommentDto );

	boolean isApplicable( CommentType aCommentType );
}
