package com.amadon.patentconnector.patent.service.componentCreator;

/**
 * Specifies creator responsible for creation one of the Patent component.
 *
 * @param <K> - type of create dto
 *
 * @param <T> - type of returned entity based on create dto
 *
 */
public interface PatentComponentCreator< K, T >
{
	T resolvePatentComponent( K aCreateComponentDto );
}
