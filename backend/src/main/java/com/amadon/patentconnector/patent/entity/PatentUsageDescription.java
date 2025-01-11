package com.amadon.patentconnector.patent.entity;

import com.amadon.patentconnector.businessBranch.entity.BusinessBranch;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table( name = "patent_usage_descriptions" )
public class PatentUsageDescription
{
	@Id
	@GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "patent_usage_descriptions_id_gen" )
	@SequenceGenerator( name = "patent_usage_descriptions_id_gen", sequenceName = "patent_usage_descriptions_id_seq",
			allocationSize = 1 )
	@Column( name = "id", nullable = false )
	private Long id;

	@ManyToOne( fetch = FetchType.LAZY, optional = false )
	@JoinColumn( name = "patent_analysis_id", nullable = false )
	private PatentAnalysisDatum patentAnalysis;

	@ManyToOne( fetch = FetchType.EAGER, optional = false )
	@JoinColumn( name = "business_branch_id", nullable = false )
	private BusinessBranch businessBranch;

	@Getter( AccessLevel.NONE )
	@Setter( AccessLevel.NONE )
	@Column( name = "description", nullable = false, length = Integer.MAX_VALUE )
	private String description;

	@Column( name = "created_at" )
	private LocalDateTime createdAt;

	@Column( name = "updated_at" )
	private LocalDateTime updatedAt;

	@Column( name = "created_by", length = 50 )
	private String createdBy;

	@Column( name = "updated_by", length = 50 )
	private String updatedBy;

	public List< String > getDescriptions()
	{
		return Arrays.stream( description.split( "," ) ).toList();
	}

	public void setDescriptions( final List<String>  descriptions )
	{
		this.description = String.join( ",", descriptions );
	}
}