package com.amadon.patentconnector.patent.service.dto.create;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link com.amadon.patentconnector.patent.entity.OtherPatentDocument}
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateOtherPatentDocumentDto implements Serializable
{
	private String documentCode;
	private String documentUri;
}