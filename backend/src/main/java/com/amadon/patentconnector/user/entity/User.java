package com.amadon.patentconnector.user.entity;

import com.amadon.patentconnector.shared.entity.Auditable;
import com.amadon.patentconnector.user.features.entrepreneurData.entity.EntrepreneursData;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table( name = "users" )
@EntityListeners( Auditable.class )
public class User implements Auditable
{
	@Id
	@GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "users_id_gen" )
	@SequenceGenerator( name = "users_id_gen", sequenceName = "users_id_seq", allocationSize = 1 )
	@Column( name = "id", nullable = false )
	private Long id;

	@Column( name = "password", nullable = false, length = 500 )
	private String password;

	@Column( name = "email", nullable = false, length = 100 )
	private String email;

	@Column( name = "name", nullable = false, length = 300 )
	private String name;

	@Column( name = "last_name", nullable = false, length = 300 )
	private String lastName;

	@Column( name = "is_active" )
	private Boolean isActive;

	@OneToOne( mappedBy = "user", fetch = FetchType.EAGER )
	private EntrepreneursData entrepreneursData;

	@Column( name = "created_at" )
	private LocalDateTime createdAt;

	@Column( name = "updated_at" )
	private LocalDateTime updatedAt;

	@Column( name = "created_by", length = 100 )
	private String createdBy;

	@Column( name = "updated_by", length = 100 )
	private String updatedBy;

}