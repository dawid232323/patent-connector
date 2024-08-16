package com.amadon.patentconnector.shared.entity;

import java.time.LocalDateTime;

public interface Auditable
{
	/**
	 * Returns the timestamp of when the entity was created.
	 *
	 * @return a {@link LocalDateTime} representing the creation time.
	 */
	LocalDateTime getCreatedAt();

	/**
	 * Sets the timestamp of when the entity was created.
	 *
	 * @param aCreatedAt a {@link LocalDateTime} representing the creation time to set.
	 */
	void setCreatedAt( LocalDateTime aCreatedAt );

	/**
	 * Returns the timestamp of the last update made to the entity.
	 *
	 * @return a {@link LocalDateTime} representing the last update time.
	 */
	LocalDateTime getUpdatedAt();

	/**
	 * Sets the timestamp of the last update made to the entity.
	 *
	 * @param aUpdatedAt a {@link LocalDateTime} representing the last update time to set.
	 */
	void setUpdatedAt( LocalDateTime aUpdatedAt );

	/**
	 * Returns the identifier of the user who created the entity.
	 *
	 * @return a {@link String} representing the creator's identifier.
	 */
	String getCreatedBy();

	/**
	 * Sets the identifier of the user who created the entity.
	 *
	 * @param aCreatedBy a {@link String} representing the creator's identifier to set.
	 */
	void setCreatedBy( String aCreatedBy );

	/**
	 * Returns the identifier of the user who last updated the entity.
	 *
	 * @return a {@link String} representing the last updater's identifier.
	 */
	String getUpdatedBy();

	/**
	 * Sets the identifier of the user who last updated the entity.
	 *
	 * @param aUpdatedBy a {@link String} representing the last updater's identifier to set.
	 */
	void setUpdatedBy( String aUpdatedBy );
}
