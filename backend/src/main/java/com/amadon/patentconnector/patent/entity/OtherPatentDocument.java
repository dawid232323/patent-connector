package com.amadon.patentconnector.patent.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table( name = "other_patent_documents" )
public class OtherPatentDocument
{
	@Id
	@GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "other_patent_documents_id_gen" )
	@SequenceGenerator( name = "other_patent_documents_id_gen", sequenceName = "other_patent_documents_id_seq",
			allocationSize = 1 )
	@Column( name = "id", nullable = false )
	private Long id;

	@ManyToOne( fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL )
	@JoinColumn( name = "application_reference_id", nullable = false )
	private ApplicationReference applicationReference;

	@Column( name = "document_code", nullable = false, length = 50 )
	private String documentCode;

	@Column( name = "document_uri", nullable = false, length = Integer.MAX_VALUE )
	private String documentUri;

	@Column( name = "created_at" )
	private LocalDateTime createdAt;

	@Column( name = "updated_at" )
	private LocalDateTime updatedAt;

}