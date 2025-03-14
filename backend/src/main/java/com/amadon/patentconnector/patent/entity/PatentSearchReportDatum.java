package com.amadon.patentconnector.patent.entity;

import com.amadon.patentconnector.shared.util.entity.AuditableEntityListener;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table( name = "patent_search_report_data" )
public class PatentSearchReportDatum
{
	@Id
	@GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "patent_search_report_data_id_gen" )
	@SequenceGenerator( name = "patent_search_report_data_id_gen", sequenceName = "patent_search_report_data_id_seq",
			allocationSize = 1 )
	@Column( name = "id", nullable = false )
	private Long id;

	@OneToOne( fetch = FetchType.EAGER, optional = false, cascade = CascadeType.ALL )
	@JoinColumn( name = "patent_id", nullable = false )
	private Patent patent;

	@Column( name = "created_at" )
	private LocalDateTime createdAt;

	@Column( name = "updated_at" )
	private LocalDateTime updatedAt;

	@OneToMany( mappedBy = "searchReportData", cascade = CascadeType.ALL, fetch = FetchType.EAGER )
	private Set< PatentCitation > patentCitations = new LinkedHashSet<>();

}