package com.amadon.patentconnector.mail.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
public enum EmailType
{
	REGISTRATION( "registration", "email/registration" );

	private final String type;
	private final String templatePath;
	}
