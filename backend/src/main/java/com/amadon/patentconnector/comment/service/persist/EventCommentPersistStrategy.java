package com.amadon.patentconnector.comment.service.persist;

import com.amadon.patentconnector.comment.entity.Comment;
import com.amadon.patentconnector.comment.entity.CommentType;
import com.amadon.patentconnector.comment.service.dto.CreateCommentDto;
import com.amadon.patentconnector.comment.service.repository.CommentRepository;
import com.amadon.patentconnector.event.entity.Event;
import com.amadon.patentconnector.event.service.repository.EventRepository;
import com.amadon.patentconnector.user.service.UserService;
import org.springframework.stereotype.Component;

@Component
public class EventCommentPersistStrategy extends AbstractCommentPersistStrategy
{

	private final EventRepository eventRepository;

	EventCommentPersistStrategy( final UserService aUserService,
								 final CommentRepository aCommentRepository,
								 final EventRepository aEventRepository )
	{
		super( aUserService, aCommentRepository );
		this.eventRepository = aEventRepository;
	}

	@Override
	public Comment createComment( final CreateCommentDto aCreateCommentDto )
	{
		final Comment createdComment = super.createComment( aCreateCommentDto );
		setEvent( createdComment, aCreateCommentDto );
		return createdComment;
	}

	@Override
	protected void setType( final Comment aComment )
	{
		aComment.setType( CommentType.EVENT );
	}

	@Override
	public boolean isApplicable( final CommentType aCommentType )
	{
		return aCommentType.equals( CommentType.EVENT );
	}

	private void setEvent( final Comment aComment, final CreateCommentDto aCreateCommentDto )
	{
		final Event event = eventRepository.findById( aCreateCommentDto.getEventId() )
				.get(); // event existence is checked during validation phase
		aComment.setEvent( event );
	}
}
