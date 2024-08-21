package com.amadon.patentconnector.researchInstitution.service.exception;

public class ResearchInstitutionNotFoundException extends RuntimeException
{
	public ResearchInstitutionNotFoundException()
	{
		super( "Institution with given id does not exist" );
	}

	public ResearchInstitutionNotFoundException( final String aMessage )
	{
		super( aMessage );
	}
}
