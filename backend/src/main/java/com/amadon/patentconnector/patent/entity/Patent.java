package com.amadon.patentconnector.patent.entity;

import com.amadon.patentconnector.shared.util.entity.AuditableEntityListener;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table( name = "patents" )
@EntityListeners( AuditableEntityListener.class )
public class Patent
{
	@Id
	@GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "patents_id_gen" )
	@SequenceGenerator( name = "patents_id_gen", sequenceName = "patents_data_id_seq", allocationSize = 1 )
	@Column( name = "id", nullable = false )
	private Long id;

	@OneToOne( mappedBy = "patent", fetch = FetchType.EAGER, cascade = CascadeType.ALL )
	private PatentAnalysisDatum patentAnalysisData;

	@OneToOne( mappedBy = "patent", fetch = FetchType.EAGER, cascade = CascadeType.ALL )
	private PatentBibliographicDatum bibliographicData;

	@OneToOne( mappedBy = "patent", fetch = FetchType.EAGER, cascade = CascadeType.ALL )
	private PatentSearchReportDatum searchReportData;

	@Column( name = "title", length = 500 )
	private String title;

	@Column( name = "description", length = Integer.MAX_VALUE )
	private String description;

	@Column( name = "abstract", length = Integer.MAX_VALUE )
	private String abstractField;

	@Column( name = "source", length = 100 )
	private String source;

	@Column( name = "patent_timestamp" )
	private Instant patentTimestamp;

	@Column( name = "begin_date" )
	private LocalDate beginDate;

	@Column( name = "end_date" )
	private LocalDate endDate;

	@Column( name = "status_id", length = 40 )
	private String statusId;

	@Column( name = "status_description", length = 100 )
	private String statusDescription;

	@Column( name = "status_code", length = 50 )
	private String statusCode;

	@Column( name = "extidappli", length = 50 )
	private String extidappli;

	@Column( name = "extidpatent", length = 50 )
	private String extidpatent;

	@Column( name = "cntrenew", length = 30 )
	private String cntrenew;

	@Column( name = "gazette_number", length = 30 )
	private String gazetteNumber;

	@Column( name = "gazette_no_spec", length = 30 )
	private String gazetteNoSpec;

	@Column( name = "gazette_kind", length = 30 )
	private String gazetteKind;

	@Column( name = "gazette_date" )
	private LocalDate gazetteDate;

	@Column( name = "gazette_uri", length = 100 )
	private String gazetteUri;

	@Column( name = "created_at" )
	private LocalDateTime createdAt;

	@Column( name = "updated_at" )
	private LocalDateTime updatedAt;

}