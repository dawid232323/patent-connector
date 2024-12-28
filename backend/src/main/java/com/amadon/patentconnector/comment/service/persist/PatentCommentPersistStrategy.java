package com.amadon.patentconnector.comment.service.persist;

import com.amadon.patentconnector.comment.entity.Comment;
import com.amadon.patentconnector.comment.entity.CommentType;
import com.amadon.patentconnector.comment.service.dto.CreateCommentDto;
import com.amadon.patentconnector.comment.service.repository.CommentRepository;
import com.amadon.patentconnector.patent.entity.Patent;
import com.amadon.patentconnector.patent.service.PatentRepository;
import com.amadon.patentconnector.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PatentCommentPersistStrategy extends AbstractCommentPersistStrategy
{
	private final PatentRepository patentRepository;

	PatentCommentPersistStrategy( final PatentRepository aPatentRepository, final CommentRepository aCommentRepository, final UserService aUserService )
	{
		super( aUserService, aCommentRepository );
		patentRepository = aPatentRepository;
	}

	@Override
	public Comment createComment( final CreateCommentDto aCreateCommentDto )
	{
		final Comment createdComment = super.createComment( aCreateCommentDto );
		setPatent( createdComment, aCreateCommentDto );
		return createdComment;
	}

	@Override
	public boolean isApplicable( final CommentType aCommentType )
	{
		return aCommentType.equals( CommentType.PATENT );
	}

	@Override
	protected void setType( final Comment aComment )
	{
		aComment.setType( CommentType.PATENT );
	}

	private void setPatent( final Comment aComment, final CreateCommentDto aCreateCommentDto )
	{
		final Patent patent = patentRepository.getPatent( aCreateCommentDto.getPatentId() );
		aComment.setPatent( patent );
	}
}
