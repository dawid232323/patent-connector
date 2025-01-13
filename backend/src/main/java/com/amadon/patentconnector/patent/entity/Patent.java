package com.amadon.patentconnector.patent.entity;

import com.amadon.patentconnector.shared.entity.Auditable;
import com.amadon.patentconnector.shared.util.entity.AuditableEntityListener;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table( name = "patents" )
@EntityListeners( AuditableEntityListener.class )
public class Patent implements Auditable
{
	@Id
	@GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "patents_id_gen" )
	@SequenceGenerator( name = "patents_id_gen", sequenceName = "patents_data_id_seq", allocationSize = 1 )
	@Column( name = "id", nullable = false )
	private Long id;

	@OneToOne( mappedBy = "patent", fetch = FetchType.EAGER, cascade = CascadeType.ALL )
	private PatentAnalysisDatum patentAnalysisData;

	@OneToOne( mappedBy = "patent", fetch = FetchType.EAGER, cascade = CascadeType.ALL )
	private PatentBibliographicDatum bibliographicData;

	@OneToOne( mappedBy = "patent", fetch = FetchType.EAGER, cascade = CascadeType.ALL )
	private PatentSearchReportDatum searchReportData;

	@Column( name = "title", length = 500 )
	private String title;

	@Column( name = "description", length = Integer.MAX_VALUE )
	private String description;

	@Column( name = "abstract", length = Integer.MAX_VALUE )
	private String abstractField;

	@Column( name = "source", length = 100 )
	private String source;

	@Column( name = "patent_timestamp" )
	private LocalDateTime patentTimestamp;

	@Column( name = "begin_date" )
	private LocalDate beginDate;

	@Column( name = "end_date" )
	private LocalDate endDate;

	@Column( name = "status_id", length = 40 )
	private String statusId;

	@Column( name = "status_description", length = 100 )
	private String statusDescription;

	@Column( name = "status_code", length = 50 )
	private String statusCode;

	@Column( name = "extidappli", length = 50 )
	private String extidappli;

	@Column( name = "extidpatent", length = 50 )
	private String extidpatent;

	@Column( name = "cntrenew", length = 30 )
	private String cntrenew;

	@Column( name = "gazette_number", length = 30 )
	private String gazetteNumber;

	@Column( name = "gazette_no_spec", length = 30 )
	private String gazetteNoSpec;

	@Column( name = "gazette_kind", length = 30 )
	private String gazetteKind;

	@Column( name = "gazette_date" )
	private LocalDate gazetteDate;

	@Column( name = "gazette_uri", length = 100 )
	private String gazetteUri;

	@Column( name = "created_at" )
	private LocalDateTime createdAt;

	@Column( name = "updated_at" )
	private LocalDateTime updatedAt;

	@Override
	public String getCreatedBy()
	{
		return "";
	}

	@Override
	public void setCreatedBy( final String aCreatedBy )
	{

	}

	@Override
	public String getUpdatedBy()
	{
		return "";
	}

	@Override
	public void setUpdatedBy( final String aUpdatedBy )
	{

	}

	@Override
	public final boolean equals( final Object aO )
	{
		if ( this == aO )
		{
			return true;
		}
		if ( aO == null )
		{
			return false;
		}
		Class< ? > oEffectiveClass = aO instanceof HibernateProxy ? ( (HibernateProxy) aO ).getHibernateLazyInitializer()
				.getPersistentClass() : aO.getClass();
		Class< ? > thisEffectiveClass = this instanceof HibernateProxy ? ( (HibernateProxy) this ).getHibernateLazyInitializer()
				.getPersistentClass() : this.getClass();
		if ( thisEffectiveClass != oEffectiveClass )
		{
			return false;
		}
		Patent patent = (Patent) aO;
		return getId() != null && Objects.equals( getId(), patent.getId() );
	}

	@Override
	public final int hashCode()
	{
		return this instanceof HibernateProxy ? ( (HibernateProxy) this ).getHibernateLazyInitializer()
				.getPersistentClass()
				.hashCode() : getClass().hashCode();
	}
}