package com.amadon.patentconnector.comment.service.dto;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCommentDto
{
	private Long parentId;
	@Nullable
	private Long patentId;
	@Nullable
	private Long eventId;
	@NotNull
	private String content;
}
