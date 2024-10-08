package com.amadon.patentconnector.patent.entity;

import com.amadon.patentconnector.businessBranch.entity.BusinessBranch;
import com.amadon.patentconnector.shared.util.entity.AuditableEntityListener;
import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table( name = "patent_analysis_data" )
@EntityListeners( AuditableEntityListener.class )
public class PatentAnalysisDatum
{
	@Id
	@GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "patent_analysis_data_id_gen" )
	@SequenceGenerator( name = "patent_analysis_data_id_gen", sequenceName = "patent_analysis_data_id_seq",
			allocationSize = 1 )
	@Column( name = "id", nullable = false )
	private Long id;

	@OneToOne( fetch = FetchType.LAZY, cascade = CascadeType.ALL )
	@JoinColumn( name = "patent_id" )
	private Patent patent;

	@ManyToMany( cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH } )
	@JoinTable( name = "patent_analysis_business_branches",
			joinColumns = @JoinColumn( name = "patent_analysis_id" ),
			inverseJoinColumns = @JoinColumn( name = "business_branch_id" ) )
	private Set< BusinessBranch > businessBranches = new LinkedHashSet<>();

}