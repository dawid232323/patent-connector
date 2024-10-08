package com.amadon.patentconnector.security.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class TokenDto
{
	private String token;
	private String refreshToken;
}
