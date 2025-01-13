package com.amadon.patentconnector.mail.dto;

import com.amadon.patentconnector.user.entity.User;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class NewPatentNotificationEmailDto
{
	private User recipient;
	private List< PatentNotificationDetails > patents;

	@Builder
	public static class PatentNotificationDetails
	{
		private String name;
		private String link;
		private List< String > businessBranches;
	}
}
