package com.amadon.patentconnector.event.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PersistEventDto
{
	@NotNull
	@Size( min = 1, max = 200 )
	private String title;

	@NotNull
	@Future
	private LocalDateTime startDate;

	@Future
	private LocalDateTime endDate;

	@Size( max = 300 )
	private String localization;

	@NotNull
	private String description;

	@Email
	@Size( max = 70 )
	private String contactEmail;

	@Size( max = 70 )
	private String contactPhone;

	@NotNull
	private String registrationDetails;

	@NotNull
	@Size( min = 1 )
	private List< Long > businessBranchesIds;
}
