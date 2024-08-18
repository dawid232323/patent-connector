package com.amadon.patentconnector.user.features.entrepreneurData.entity;

import com.amadon.patentconnector.shared.entity.Auditable;
import com.amadon.patentconnector.shared.util.entity.AuditableEntityListener;
import com.amadon.patentconnector.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table( name = "entrepreneurs_data" )
@EntityListeners( AuditableEntityListener.class )
public class EntrepreneursData implements Auditable
{
	@Id
	@GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "entrepreneurs_data_id_gen" )
	@SequenceGenerator( name = "entrepreneurs_data_id_gen", sequenceName = "entrepreneurs_data_id_seq",
			allocationSize = 1 )
	@Column( name = "id", nullable = false )
	private Long id;

	@OneToOne( fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL )
	@JoinColumn( name = "users_id", nullable = false )
	private User user;

	@Column( name = "company_name", length = 500 )
	private String companyName;

	@Column( name = "nip", length = 20 )
	private String nip;

	@Column( name = "regon", length = 15 )
	private String regon;

	@Column( name = "created_at" )
	private LocalDateTime createdAt;

	@Column( name = "updated_at" )
	private LocalDateTime updatedAt;

	@Column( name = "created_by", length = 100 )
	private String createdBy;

	@Column( name = "updated_by", length = 100 )
	private String updatedBy;
}