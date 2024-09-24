package com.amadon.patentconnector.patent.entity;

import com.amadon.patentconnector.shared.util.entity.AuditableEntityListener;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table( name = "patent_citations" )
@EntityListeners( AuditableEntityListener.class )
public class PatentCitation
{
	@Id
	@GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "patent_citations_id_gen" )
	@SequenceGenerator( name = "patent_citations_id_gen", sequenceName = "patent_citations_id_seq", allocationSize =
			1 )
	@Column( name = "id", nullable = false )
	private Long id;

	@ManyToOne( fetch = FetchType.LAZY )
	@JoinColumn( name = "search_report_data_id" )
	private PatentSearchReportDatum searchReportData;

	@Column( name = "document_country", length = 30 )
	private String documentCountry;

	@Column( name = "document_number", length = 30 )
	private String documentNumber;

	@Column( name = "document_kind", length = 30 )
	private String documentKind;

	@Column( name = "document_publication_date", length = 30 )
	private String documentPublicationDate;

	@Column( name = "document_name", length = 200 )
	private String documentName;

	@Column( name = "citation_text", length = Integer.MAX_VALUE )
	private String citationText;

	@Column( name = "citation_category", length = 10 )
	private String citationCategory;

	@Column( name = "rel_claims", length = 100 )
	private String relClaims;

}