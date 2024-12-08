package com.amadon.patentconnector.event.entity;

import com.amadon.patentconnector.businessBranch.entity.BusinessBranch;
import com.amadon.patentconnector.shared.entity.Auditable;
import com.amadon.patentconnector.shared.util.entity.AuditableEntityListener;
import com.amadon.patentconnector.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table( name = "events" )
@EntityListeners( AuditableEntityListener.class )
public class Event implements Auditable
{
	@Id
	@GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "events_id_gen" )
	@SequenceGenerator( name = "events_id_gen", sequenceName = "events_id_seq", allocationSize = 1 )
	@Column( name = "id", nullable = false )
	private Long id;

	@Column( name = "title", nullable = false, length = 200 )
	private String title;

	@Column( name = "start_date", nullable = false )
	private LocalDateTime startDate;

	@Column( name = "end_date" )
	private LocalDateTime endDate;

	@Column( name = "localization", length = 300 )
	private String localization;

	@ManyToOne( fetch = FetchType.EAGER, optional = false, cascade = CascadeType.ALL )
	@JoinColumn( name = "caregiver_id", nullable = false )
	private User caregiver;

	@Column( name = "description", nullable = false, length = Integer.MAX_VALUE )
	private String description;

	@Column( name = "contact_email", length = 70 )
	private String contactEmail;

	@Column( name = "contact_phone", length = 70 )
	private String contactPhone;

	@Column( name = "registration_details", nullable = false, length = Integer.MAX_VALUE )
	private String registrationDetails;

	@Column( name = "created_at" )
	private LocalDateTime createdAt;

	@Column( name = "updated_at" )
	private LocalDateTime updatedAt;

	@Column( name = "created_by", length = 50 )
	private String createdBy;

	@Column( name = "updated_by", length = 50 )
	private String updatedBy;

	@Column( name = "deleted_on" )
	private LocalDateTime deletedOn;

	@Column( name = "deleted_by" )
	private String deletedBy;

	@ManyToMany( fetch = FetchType.EAGER, cascade = CascadeType.ALL )
	@JoinTable( name = "events_business_branches_ids",
			joinColumns = @JoinColumn( name = "event_id" ),
			inverseJoinColumns = @JoinColumn( name = "business_branch_id" ) )
	private Set< BusinessBranch > businessBranches = new LinkedHashSet<>();

}