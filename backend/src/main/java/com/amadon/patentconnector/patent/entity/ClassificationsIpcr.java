package com.amadon.patentconnector.patent.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table( name = "classifications_ipcr" )
public class ClassificationsIpcr
{
	@Id
	@GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "classifications_ipcr_id_gen" )
	@SequenceGenerator( name = "classifications_ipcr_id_gen", sequenceName = "classifications_ipcr_id_seq",
			allocationSize = 1 )
	@Column( name = "id", nullable = false )
	private Long id;

	@ManyToOne( fetch = FetchType.LAZY, optional = false )
	@JoinColumn( name = "bibliographic_data_id", nullable = false )
	private PatentBibliographicDatum bibliographicData;

	@Column( name = "ipc_version_indicator_date" )
	private LocalDate ipcVersionIndicatorDate;

	@Column( name = "ipcr_section", length = 10 )
	private String ipcrSection;

	@Column( name = "ipcr_class", length = 50 )
	private String ipcrClass;

	@Column( name = "ipcr_subclass", length = 50 )
	private String ipcrSubclass;

	@Column( name = "ipcr_main_group", length = 50 )
	private String ipcrMainGroup;

	@Column( name = "ipcr_subgroup", length = 50 )
	private String ipcrSubgroup;

	@Column( name = "created_at" )
	private LocalDateTime createdAt;

	@Column( name = "updated_at" )
	private LocalDateTime updatedAt;

}