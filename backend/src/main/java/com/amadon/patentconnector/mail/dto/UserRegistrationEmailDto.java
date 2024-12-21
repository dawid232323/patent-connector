package com.amadon.patentconnector.mail.dto;

import com.amadon.patentconnector.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationEmailDto
{
	private User user;
	private String token;
}
