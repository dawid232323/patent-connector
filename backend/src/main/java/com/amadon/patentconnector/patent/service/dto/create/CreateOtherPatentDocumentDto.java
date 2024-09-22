package com.amadon.patentconnector.patent.service.dto.create;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

/**
 * DTO for {@link com.amadon.patentconnector.patent.entity.OtherPatentDocument}
 */
@AllArgsConstructor
@Getter
public class CreateOtherPatentDocumentDto implements Serializable
{
	private final String documentCode;
	private final String documentUri;
}