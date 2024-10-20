package com.amadon.patentconnector.patent.entity;

import com.amadon.patentconnector.shared.util.entity.AuditableEntityListener;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table( name = "patent_address_books" )
public class PatentAddressBook
{
	@Id
	@GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "patent_address_books_id_gen" )
	@SequenceGenerator( name = "patent_address_books_id_gen", sequenceName = "patent_address_books_id_seq",
			allocationSize = 1 )
	@Column( name = "id", nullable = false )
	private Long id;

	@Column( name = "address_book_type", nullable = false, length = 50 )
	private AddressBookTypeEnum addressBookType;

	@Column( name = "name", length = 300 )
	private String name;

	@Column( name = "first_name", length = 300 )
	private String firstName;

	@Column( name = "last_name", length = 300 )
	private String lastName;

	@Column( name = "organisation_name", length = 300 )
	private String organisationName;

	@Column( name = "address_postal_code", length = 300 )
	private String addressPostalCode;

	@Column( name = "address_city", length = 300 )
	private String addressCity;

	@Column( name = "address_township", length = 300 )
	private String addressTownship;

	@Column( name = "address_county", length = 300 )
	private String addressCounty;

	@Column( name = "address_state", length = 300 )
	private String addressState;

	@Column( name = "address_country", length = 300 )
	private String addressCountry;

	@Column( name = "nationality_country", length = 300 )
	private String nationalityCountry;

	@Column( name = "residence_country", length = 300 )
	private String residenceCountry;

	@Column( name = "designated_states", length = 300 )
	private String designatedStates;

	@Builder.Default
	@ManyToMany( mappedBy = "agents", cascade = CascadeType.ALL )
	private Set< PatentBibliographicDatum > bibliographicDataAgents = new HashSet<>();

	@Builder.Default
	@ManyToMany( mappedBy = "applicants", cascade = CascadeType.ALL )
	private Set< PatentBibliographicDatum > bibliographicDataApplicants = new HashSet<>();

	@Builder.Default
	@ManyToMany( mappedBy = "assignees", cascade = CascadeType.ALL )
	private Set< PatentBibliographicDatum > bibliographicDataAssignees = new HashSet<>();

	@Builder.Default
	@ManyToMany( mappedBy = "inventors", cascade = CascadeType.ALL )
	private Set< PatentBibliographicDatum > bibliographicDataInventors = new HashSet<>();

	@Column( name = "created_at" )
	private LocalDateTime createdAt;

	@Column( name = "updated_at" )
	private LocalDateTime updatedAt;

	public void addBibliographicDataMember( final PatentBibliographicDatum aBibliographicDatum, final AddressBookTypeEnum aTypeEnum )
	{
		switch ( aTypeEnum )
		{
			case AGENT -> getBibliographicDataAgents().add( aBibliographicDatum );
			case ASSIGNEE -> getBibliographicDataAssignees().add( aBibliographicDatum );
			case INVENTOR -> getBibliographicDataInventors().add( aBibliographicDatum );
			case APPLICANT -> getBibliographicDataApplicants().add( aBibliographicDatum );
			case null, default -> throw new IllegalArgumentException( "Bibliographic data member type must not be null" );
		}
	}

}