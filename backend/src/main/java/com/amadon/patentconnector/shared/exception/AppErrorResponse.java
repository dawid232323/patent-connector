package com.amadon.patentconnector.shared.exception;

import com.amadon.patentconnector.shared.constants.DomainCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppErrorResponse
{
	private HttpStatus status;
	private String errorTitle;
	private String message;
	private DomainCode domainCode;
	private String errorCause;
	private String uuid;
	private String originalExceptionMessage;
}
