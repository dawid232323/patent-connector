package com.amadon.patentconnector.comment.service.mapper;

import com.amadon.patentconnector.comment.entity.Comment;
import com.amadon.patentconnector.comment.service.dto.CommentDto;
import com.amadon.patentconnector.user.entity.User;
import com.amadon.patentconnector.user.entity.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public abstract class CommentMapperDecorator implements CommentMapper
{
	@Autowired
	@Qualifier( "delegate" )
	private CommentMapper mapperDelegate;

	@Override
	public CommentDto toDto( final Comment comment )
	{
		final CommentDto commentDto = mapperDelegate.toDto( comment );
		final User author = comment.getAuthor();
		if ( isResearchWorker( author ) )
		{
			commentDto.setAuthorAffiliation( getResearchWorkerAffiliation( author ) );
		} else if ( isEntrepreneur( author ) )
		{
			commentDto.setAuthorAffiliation( getEntrepreneurAffiliation( author ) );
		}
		return commentDto;
	}

	private boolean isResearchWorker( final User aUser )
	{
		return aUser.getAuthorities()
				.contains( UserRole.RESEARCH_WORKER );
	}

	private boolean isEntrepreneur( final User aUser )
	{
		return aUser.getAuthorities()
				.contains( UserRole.ENTREPRENEUR );
	}

	private String getEntrepreneurAffiliation( final User aUser )
	{
		return aUser.getEntrepreneursData().getCompanyName();
	}

	private String getResearchWorkerAffiliation( final User aUser )
	{
		return aUser.getResearchInstitution().getName();
	}
}
