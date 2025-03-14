package com.amadon.patentconnector.comment.entity;

import com.amadon.patentconnector.event.entity.Event;
import com.amadon.patentconnector.patent.entity.Patent;
import com.amadon.patentconnector.shared.entity.Auditable;
import com.amadon.patentconnector.shared.util.entity.AuditableEntityListener;
import com.amadon.patentconnector.user.entity.User;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.Where;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table( name = "comments" )
@EntityListeners( AuditableEntityListener.class )
public class Comment implements Auditable
{
	@Id
	@GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "comments_id_gen" )
	@SequenceGenerator( name = "comments_id_gen", sequenceName = "comments_id_seq", allocationSize = 1 )
	@Column( name = "id", nullable = false )
	private Long id;

	@ManyToOne( fetch = FetchType.LAZY )
	@JoinColumn( name = "parent_id", nullable = true )
	private Comment parent;

	@SQLRestriction( "deleted_on is null" )
	@OneToMany( mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true )
	private List< Comment > replies = new ArrayList<>();

	@ManyToOne( fetch = FetchType.EAGER, optional = false )
	@JoinColumn( name = "author_id", nullable = false )
	private User author;

	@Nullable
	@JoinTable(
			name = "patent_comments",
			joinColumns = @JoinColumn( name = "comment_id" ),
			inverseJoinColumns = @JoinColumn( name = "patent_id" )
	)
	@ManyToOne( cascade = CascadeType.ALL )
	private Patent patent;

	@Nullable
	@JoinTable(
			name = "event_comments",
			joinColumns = @JoinColumn( name = "comment_id" ),
			inverseJoinColumns = @JoinColumn( name = "event_id" )
	)
	@ManyToOne( cascade = CascadeType.ALL )
	private Event event;

	@Getter( AccessLevel.NONE )
	@Setter( AccessLevel.NONE )
	@Column( name = "type", nullable = false, length = 20 )
	private String type;

	@Column( name = "content", nullable = false, length = Integer.MAX_VALUE )
	private String content;

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

	@Column( name = "deleted_by", length = 100 )
	private String deletedBy;

	public CommentType getType()
	{
		return CommentType.valueOf( type );
	}

	public void setType( CommentType type )
	{
		this.type = type.name();
	}
}