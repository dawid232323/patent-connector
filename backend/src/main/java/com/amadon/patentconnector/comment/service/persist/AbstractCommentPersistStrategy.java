package com.amadon.patentconnector.comment.service.persist;

import com.amadon.patentconnector.comment.entity.Comment;
import com.amadon.patentconnector.comment.service.dto.CreateCommentDto;
import com.amadon.patentconnector.comment.service.repository.CommentRepository;
import com.amadon.patentconnector.user.entity.User;
import com.amadon.patentconnector.user.service.UserService;

import java.util.Objects;

public abstract class AbstractCommentPersistStrategy implements CommentPersistStrategy
{
	private final UserService userService;
	private final CommentRepository commentRepository;

	AbstractCommentPersistStrategy( final UserService aUserService, final CommentRepository aCommentRepository )
	{
		userService = aUserService;
		commentRepository = aCommentRepository;
	}

	protected abstract void setType( final Comment aComment );

	@Override
	public Comment createComment( final CreateCommentDto aCreateCommentDto )
	{
		final Comment createdComment = new Comment();
		setCommonAttributes( createdComment, aCreateCommentDto );
		setParentComment( createdComment, aCreateCommentDto );
		setType( createdComment );
		setAuthor( createdComment );
		return createdComment;
	}

	private void setCommonAttributes( final Comment aComment, final CreateCommentDto aCreateCommentDto )
	{
		aComment.setContent( aComment.getContent() );
	}

	private void setParentComment( final Comment aComment, final CreateCommentDto aCreateCommentDto )
	{
		if ( Objects.isNull( aCreateCommentDto.getParentId() ) )
		{
			return;
		}
		final Comment parentComment = commentRepository.findById( aCreateCommentDto.getParentId() )
				.get(); // parent existence is checked during validation phase
		aComment.setParent( parentComment );
	}

	private void setAuthor( final Comment aComment )
	{
		final User author = userService.getLoggedUser();
		aComment.setAuthor( author );
	}
}