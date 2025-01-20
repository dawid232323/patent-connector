package com.amadon.patentconnector.comment.service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class CommentDto
{
	private Long id;
	private Long parentId;
	private String authorName;
	private String authorEmail;
	private String authorLastName;
	private String authorAffiliation;
	private String content;
	private LocalDateTime createdAt;
	private List< CommentDto > replies;
}
