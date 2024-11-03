package com.amadon.patentconnector.inventionDemand.entity;

import com.amadon.patentconnector.businessBranch.entity.BusinessBranch;
import com.amadon.patentconnector.user.entity.User;
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
@Table( name = "invention_demands" )
public class InventionDemand
{
	@Id
	@GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "invention_demand_id_seq" )
	@SequenceGenerator( name = "invention_demand_id_seq", sequenceName = "invention_demand_id_seq",
			allocationSize = 1 )
	@Column( name = "id", nullable = false )
	private Long id;

	@OneToOne( fetch = FetchType.EAGER, optional = false, cascade = CascadeType.ALL )
	@JoinColumn( name = "issuer_id", nullable = false )
	private User issuer;

	@OneToOne( fetch = FetchType.EAGER, optional = false, cascade = CascadeType.ALL )
	@JoinColumn( name = "recipient_id", nullable = false )
	private User recipient;

	@Column( name = "demand_content", nullable = false )
	private String demandContent;

	@Builder.Default
	@ManyToMany( cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH } )
	@JoinTable( name = "invention_demands_business_branches", joinColumns = @JoinColumn( name = "invention_demand_id" ),
			inverseJoinColumns = @JoinColumn( name = "business_branches_id" ) )
	private Set< BusinessBranch > businessBranches = new LinkedHashSet<>();
}
