package com.amadon.patentconnector.patent.entity;

import com.amadon.patentconnector.shared.util.entity.AuditableEntityListener;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table( name = "dates_of_public_availability" )
@EntityListeners( AuditableEntityListener.class )
public class DatesOfPublicAvailability
{
	@Id
	@GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "dates_of_public_availability_id_gen" )
	@SequenceGenerator( name = "dates_of_public_availability_id_gen", sequenceName =
			"dates_of_public_availability_id_seq", allocationSize = 1 )
	@Column( name = "id", nullable = false )
	private Long id;

	@OneToOne( fetch = FetchType.LAZY )
	@JoinColumn( name = "bibliographic_data_id" )
	private PatentBibliographicDatum bibliographicData;

	@Column( name = "unexamined_printed_without_grant_document_country", length = 10 )
	private String unexaminedPrintedWithoutGrantDocumentCountry;

	@Column( name = "unexamined_printed_without_grant_document_number", length = 50 )
	private String unexaminedPrintedWithoutGrantDocumentNumber;

	@Column( name = "unexamined_printed_without_grant_document_date", length = 50 )
	private String unexaminedPrintedWithoutGrantDocumentDate;

}