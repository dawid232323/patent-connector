package com.amadon.patentconnector.comment.service.mapper;

import com.amadon.patentconnector.comment.entity.Comment;
import com.amadon.patentconnector.comment.service.dto.CommentDto;
import org.mapstruct.*;

@DecoratedWith( CommentMapperDecorator.class )
@Mapper( unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS )
public interface CommentMapper
{
	@Mapping( target = "parentId", source = "parent.id" )
	@Mapping( target = "authorName", source = "author.name" )
	@Mapping( target = "authorLastName", source = "author.lastName" )
	@Mapping( target = "authorAffiliation", ignore = true )
		// set in decorator
	CommentDto toDto( Comment comment );
}