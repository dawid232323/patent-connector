package com.amadon.patentconnector.patent.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table( name = "patent_bibliographic_data" )
public class PatentBibliographicDatum
{
	@Id
	@GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "patent_bibliographic_data_id_gen" )
	@SequenceGenerator( name = "patent_bibliographic_data_id_gen", sequenceName = "patent_bibliographic_data_id_seq",
			allocationSize = 1 )
	@Column( name = "id", nullable = false )
	private Long id;

	@OneToOne( fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL )
	@JoinColumn( name = "patent_id", nullable = false )
	private Patent patent;

	@OneToOne( fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "bibliographicData" )
	private ApplicationReference applicationReference;

	@OneToOne( fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "bibliographicData" )
	private ClassificationsIpcr classificationsIpcr;

	@OneToOne( fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "bibliographicData" )
	private DatesOfPublicAvailability datesOfPublicAvailability;

	@Column( name = "publication_document_country", length = 10 )
	private String publicationDocumentCountry;

	@Column( name = "publication_document_number", length = 50 )
	private String publicationDocumentNumber;

	@Column( name = "publication_document_kind", length = 50 )
	private String publicationDocumentKind;

	@Column( name = "publication_document_date", length = 30 )
	private String publicationDocumentDate;

	@Column( name = "ipc_classification_edition", length = 10 )
	private String ipcClassificationEdition;

	@Column( name = "ipc_main_classification", length = 50 )
	private String ipcMainClassification;

	@Column( name = "ipc_further_classifications", length = Integer.MAX_VALUE )
	private String ipcFurtherClassifications;

	@Column( name = "cpc_classification_edition", length = 10 )
	private String cpcClassificationEdition;

	@Column( name = "cpc_main_classification", length = 50 )
	private String cpcMainClassification;

	@Column( name = "cpc_further_classification", length = Integer.MAX_VALUE )
	private String cpcFurtherClassification;

	@Column( name = "invention_title", length = Integer.MAX_VALUE )
	private String inventionTitle;

	@Column( name = "invention_title_eng", length = Integer.MAX_VALUE )
	private String inventionTitleEng;

	@Column( name = "claims", length = Integer.MAX_VALUE )
	private String claims;

	@Column( name = "created_at" )
	private Instant createdAt;

	@Column( name = "updated_at" )
	private Instant updatedAt;

	@ManyToMany( cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH } )
	@JoinTable( name = "patent_bibliographic_data_agents", joinColumns = @JoinColumn( name = "bibliographic_data_id" ),
			inverseJoinColumns = @JoinColumn( name = "agent_id" ) )
	private Set< PatentAddressBook > agents = new LinkedHashSet<>();

	@ManyToMany( cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH } )
	@JoinTable( name = "patent_bibliographic_data_applicants", joinColumns = @JoinColumn( name =
			"bibliographic_data_id" ),
			inverseJoinColumns = @JoinColumn( name = "applicant_id" ) )
	private Set< PatentAddressBook > applicants = new LinkedHashSet<>();

	@ManyToMany( cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH } )
	@JoinTable( name = "patent_bibliographic_data_assignees", joinColumns = @JoinColumn( name = "bibliographic_data_id"
	),
			inverseJoinColumns = @JoinColumn( name = "assignee_id" ) )
	private Set< PatentAddressBook > assignees = new LinkedHashSet<>();

	@ManyToMany( cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH } )
	@JoinTable( name = "patent_bibliographic_data_inventors", joinColumns = @JoinColumn( name = "bibliographic_data_id"
	),
			inverseJoinColumns = @JoinColumn( name = "inventor_id" ) )
	private Set< PatentAddressBook > inventors = new LinkedHashSet<>();

}