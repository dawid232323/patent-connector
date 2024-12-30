package com.amadon.patentconnector.comment.service.persist;

import com.amadon.patentconnector.comment.entity.Comment;
import com.amadon.patentconnector.comment.entity.CommentType;
import com.amadon.patentconnector.comment.service.dto.CreateCommentDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentPersistStrategy
{
	Comment createComment( CreateCommentDto aCreateCommentDto );

	Page< Comment > getCommentsForObject( Long aObjectId, Pageable aPageable );

	void deleteComment( Long aCommentId );

	Comment updateComment( final String aUpdatedContent, final Long aCommentId );

	boolean isApplicable( CommentType aCommentType );

	default Page< Comment > getCommentsForObject( Long aObjectId )
	{
		return getCommentsForObject( aObjectId, Pageable.ofSize( 20 ) );
	}
}
