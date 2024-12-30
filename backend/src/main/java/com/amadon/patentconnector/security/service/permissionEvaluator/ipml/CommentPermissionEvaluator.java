package com.amadon.patentconnector.security.service.permissionEvaluator.ipml;

import com.amadon.patentconnector.comment.entity.Comment;
import com.amadon.patentconnector.comment.service.repository.CommentRepository;
import com.amadon.patentconnector.security.service.permissionEvaluator.EntityObjectType;
import com.amadon.patentconnector.security.service.permissionEvaluator.EntityTypePermissionEvaluatorStrategy;
import com.amadon.patentconnector.user.entity.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentPermissionEvaluator implements EntityTypePermissionEvaluatorStrategy
{

	private final CommentRepository commentRepository;

	@Override
	public boolean hasPermissionForObject( final Long aCommentId, final Authentication aAuthentication )
	{
		final Comment comment = commentRepository.findById( aCommentId )
				.orElse( null );
		if ( comment == null )
		{
			return false;
		}
		if ( aAuthentication.getAuthorities()
				.contains( UserRole.ADMIN ) )
		{
			return true;
		}
		return comment.getAuthor()
				.getEmail()
				.equals( aAuthentication.getPrincipal() );
	}

	@Override
	public boolean applicable( final EntityObjectType aObjectType )
	{
		return aObjectType.equals( EntityObjectType.COMMENT );
	}
}
