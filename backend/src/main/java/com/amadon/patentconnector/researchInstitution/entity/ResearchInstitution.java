package com.amadon.patentconnector.researchInstitution.entity;

import com.amadon.patentconnector.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table( name = "research_institutions" )
public class ResearchInstitution
{
	@Id
	@GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "research_institutions_id_gen" )
	@SequenceGenerator( name = "research_institutions_id_gen", sequenceName = "research_institutions_id_seq",
			allocationSize = 1 )
	@Column( name = "id", nullable = false )
	private Long id;

	@Column( name = "uuid", nullable = false, length = 100 )
	private String uuid;

	@Column( name = "name", nullable = false, length = 500 )
	private String name;

	@Column( name = "created" )
	private LocalDate created;

	@Column( name = "supervisor", length = 500 )
	private String supervisor;

	@Column( name = "kind", nullable = false, length = 100 )
	private String kind;

	@Column( name = "is_national_research_institute" )
	private Boolean isNationalResearchInstitute;

	@Column( name = "type", length = 70 )
	private String type;

	@Column( name = "research_institution_type", length = 150 )
	private String researchInstitutionType;

	@Column( name = "regon", length = 15 )
	private String regon;

	@Column( name = "nip", length = 20 )
	private String nip;

	@Column( name = "nip2", length = 20 )
	private String nip2;

	@Column( name = "krs", length = 50 )
	private String krs;

	@Column( name = "website_address", length = 200 )
	private String websiteAddress;

	@Column( name = "email", nullable = false, length = 100 )
	private String email;

	@Column( name = "phone_number", length = 100 )
	private String phoneNumber;

	@Column( name = "country", length = 100 )
	private String country;

	@Column( name = "postal_code", length = 20 )
	private String postalCode;

	@Column( name = "state", length = 100 )
	private String state;

	@Column( name = "city", length = 100 )
	private String city;

	@Column( name = "street", length = 100 )
	private String street;

	@Column( name = "street_number", length = 100 )
	private String streetNumber;

	@OneToMany( mappedBy = "researchInstitution", cascade = CascadeType.ALL )
	private Set< User > users = new LinkedHashSet<>();

}