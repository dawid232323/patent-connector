package com.amadon.patentconnector.patent.service.dto.create;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link com.amadon.patentconnector.patent.entity.ClassificationsIpcr}
 */
@AllArgsConstructor
@Getter
public class CreateClassificationsIpcrDto implements Serializable
{
	private final LocalDate ipcVersionIndicatorDate;
	private final String ipcrSection;
	private final String ipcrClass;
	private final String ipcrSubclass;
	private final String ipcrMainGroup;
	private final String ipcrSubgroup;
}