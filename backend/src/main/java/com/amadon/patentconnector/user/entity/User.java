package com.amadon.patentconnector.user.entity;

import com.amadon.patentconnector.researchInstitution.entity.ResearchInstitution;
import com.amadon.patentconnector.shared.entity.Auditable;
import com.amadon.patentconnector.shared.util.entity.AuditableEntityListener;
import com.amadon.patentconnector.user.features.entrepreneurData.entity.EntrepreneursData;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@Table( name = "users" )
@EntityListeners( AuditableEntityListener.class )
public class User implements Auditable, UserDetails
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

	@Column( name = "secret_key" )
	private String secretKey;

	@OneToOne( mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL )
	private EntrepreneursData entrepreneursData;

	@Column( name = "created_at" )
	private LocalDateTime createdAt;

	@Column( name = "updated_at" )
	private LocalDateTime updatedAt;

	@Column( name = "created_by", length = 100 )
	private String createdBy;

	@Column( name = "updated_by", length = 100 )
	private String updatedBy;

	@Nullable
	@ManyToOne( fetch = FetchType.LAZY, cascade = CascadeType.ALL )
	@JoinColumn( name = "research_institution_id", nullable = true )
	private ResearchInstitution researchInstitution;

	@Getter( AccessLevel.NONE )
	@Setter( AccessLevel.NONE )
	@Column( name = "roles" )
	private String roles;

	@Override
	public Collection< UserRole > getAuthorities()
	{
		if ( Objects.isNull( roles ) )
		{
			return List.of();
		}
		final List< String > separatedRoles = List.of( roles.split( "," ) );
		return separatedRoles.stream()
				.map( UserRole::fromString )
				.collect( Collectors.toList() );
	}

	public void setAuthorities( final UserRole... aUserRoles )
	{
		this.roles = Arrays.stream( aUserRoles )
				.map( UserRole::getAuthority )
				.collect( Collectors.joining( "," ) );
	}

	@Override
	public String getUsername()
	{
		return email;
	}
}