package com.amadon.patentconnector.user.features.entrepreneurData.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import javax.validation.constraints.Size;

import static com.amadon.patentconnector.shared.constants.ValidationMessages.StringLengthMessages.MAX_500_CHARACTERS;

/**
 * DTO for {@link com.amadon.patentconnector.user.features.entrepreneurData.entity.EntrepreneursData}
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CreateEntrepreneursDataDto implements Serializable
{
	@Size( max = 500, message = MAX_500_CHARACTERS )
	private String companyName;

	private String nip;

	private String regon;

	private Boolean recommendationAgreement;
}