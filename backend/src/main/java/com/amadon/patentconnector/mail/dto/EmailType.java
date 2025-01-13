package com.amadon.patentconnector.mail.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EmailType
{
	REGISTRATION( "registration", "email/registration" ),
	INVENTION_DEMAND( "inventionDemand", "email/invention-demand" ),
	NEW_PATENTS_NOTIFICATION( "newPatientsNotification", "email/new-patents-notification" );

	private final String type;
	private final String templatePath;
}
