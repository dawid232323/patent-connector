package com.amadon.patentconnector.businessBranch.entity;

import com.amadon.patentconnector.shared.entity.Auditable;
import com.amadon.patentconnector.shared.util.entity.AuditableEntityListener;
import com.amadon.patentconnector.user.features.entrepreneurData.entity.EntrepreneursData;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table( name = "business_branches" )
@EntityListeners( AuditableEntityListener.class )
public class BusinessBranch implements Auditable
{
	@Id
	@GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "business_branches_id_gen" )
	@SequenceGenerator( name = "business_branches_id_gen", sequenceName = "business_branches_id_seq", allocationSize =
			1 )
	@Column( name = "id", nullable = false )
	private Long id;

	@Column( name = "section", length = 10 )
	private String section;

	@Column( name = "department", length = 10 )
	private String department;

	@Column( name = "business_branch_group", length = 20 )
	private String businessBranchGroup;

	@Column( name = "business_branch_class", length = 20 )
	private String businessBranchClass;

	@Column( name = "code", length = 50 )
	private String code;

	@Column( name = "display_name", nullable = false, length = 300 )
	private String displayName;

	@ManyToMany( mappedBy = "interestedBusinessBranches", cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH } )
	private Set< EntrepreneursData > assignedEntrepreneurs = new HashSet<>();

	@Column( name = "created_at" )
	private LocalDateTime createdAt;

	@Column( name = "updated_at" )
	private LocalDateTime updatedAt;

	@Column( name = "created_by", length = 100 )
	private String createdBy;

	@Column( name = "updated_by", length = 100 )
	private String updatedBy;

}