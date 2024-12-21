package com.amadon.patentconnector.mail.service;

import com.amadon.patentconnector.mail.dto.EmailType;
import jakarta.mail.MessagingException;

/**
 * Interface that should be implemented for every email type to be sent
 * @param <T> type of dto containing essential data for email message
 */
public interface MailSender<T>
{
	/**
	 * Method that creates email message body and required headers and then sends it
	 *
	 * @param messageData object that contains all necessary data for message to be sent
	 */
	void send( T messageData );

	/**
	 * Method that creates email message body and required headers and then sends it
	 *
	 * @param messageData object that contains all necessary data for message to be sent
	 *
	 * @param recipient message recipient
	 */
	void send( T messageData, String recipient );

	/**
	 * Determines if strategy implementation should be used for given email type
	 *
	 * @param emailType type of email message
	 *
	 * @return true if strategy can be used for given type, false if not
	 */
	boolean isApplicable( EmailType emailType );
}
