package com.amadon.patentconnector.comment.service;

import com.amadon.patentconnector.comment.entity.Comment;
import com.amadon.patentconnector.comment.entity.CommentType;
import com.amadon.patentconnector.comment.service.dto.CommentDto;
import com.amadon.patentconnector.comment.service.dto.CreateCommentDto;
import com.amadon.patentconnector.comment.service.mapper.CommentMapper;
import com.amadon.patentconnector.comment.service.persist.CommentPersistStrategy;
import com.amadon.patentconnector.comment.service.repository.CommentRepository;
import com.amadon.patentconnector.comment.service.validation.CommentValidationRuleEngine;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService
{
	private final CommentRepository commentRepository;
	private final CommentValidationRuleEngine validationRuleEngine;
	private final List< CommentPersistStrategy > persistStrategies;
	private final CommentMapper commentMapper;

	public CommentDto createComment( final CreateCommentDto createCommentDto, final CommentType aCommentType )
	{
		validationRuleEngine.validateCreateComment( createCommentDto, aCommentType );
		final CommentPersistStrategy persistStrategy = persistStrategies.stream()
				.filter( strategy -> strategy.isApplicable( aCommentType ) )
				.findFirst()
				.orElseThrow();
		final Comment comment = persistStrategy.createComment( createCommentDto );
		commentRepository.save( comment );
		return commentMapper.toDto( comment );
	}
}
