package com.amadon.patentconnector.patent.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table( name = "application_references" )
public class ApplicationReference
{
	@Id
	@GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "application_references_id_gen" )
	@SequenceGenerator( name = "application_references_id_gen", sequenceName = "application_references_id_seq",
			allocationSize = 1 )
	@Column( name = "id", nullable = false )
	private Long id;

	@OneToOne( fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL )
	@JoinColumn( name = "bibliographic_data_id", nullable = false )
	private PatentBibliographicDatum bibliographicData;

	@Column( name = "document_country_id", length = 50 )
	private String documentCountryId;

	@Column( name = "document_number", length = 100 )
	private String documentNumber;

	@Column( name = "document_kind", length = 100 )
	private String documentKind;

	@Column( name = "document_date", length = 30 )
	private String documentDate;

	@Column( name = "created_at" )
	private LocalDateTime createdAt;

	@Column( name = "updated_at" )
	private LocalDateTime updatedAt;

	@OneToMany( mappedBy = "applicationReference", cascade = CascadeType.ALL )
	private Set< OtherPatentDocument > otherPatentDocuments = new LinkedHashSet<>();

}