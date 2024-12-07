package com.amadon.patentconnector.event.service.dto;

import com.amadon.patentconnector.businessBranch.service.dto.BusinessBranchDto;
import com.amadon.patentconnector.user.service.dto.UserDto;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * DTO for {@link com.amadon.patentconnector.event.entity.Event}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventDto implements Serializable
{
	private Long id;
	private String title;
	private LocalDateTime startDate;
	private LocalDateTime endDate;
	private String localization;
	private UserDto caregiver;
	private String description;
	private String contactEmail;
	private String contactPhone;
	private String registrationDetails;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private Set< BusinessBranchDto > businessBranches = new LinkedHashSet<>();
}