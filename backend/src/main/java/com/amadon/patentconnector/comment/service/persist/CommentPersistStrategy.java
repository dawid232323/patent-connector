package com.amadon.patentconnector.comment.service.persist;

import com.amadon.patentconnector.comment.entity.Comment;
import com.amadon.patentconnector.comment.entity.CommentType;
import com.amadon.patentconnector.comment.service.dto.CreateCommentDto;

public interface CommentPersistStrategy
{
	Comment createComment( CreateCommentDto aCreateCommentDto );

	boolean isApplicable( CommentType aCommentType );
}
