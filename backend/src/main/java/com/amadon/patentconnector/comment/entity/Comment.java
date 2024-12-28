package com.amadon.patentconnector.comment.entity;

import com.amadon.patentconnector.event.entity.Event;
import com.amadon.patentconnector.patent.entity.Patent;
import com.amadon.patentconnector.shared.util.entity.AuditableEntityListener;
import com.amadon.patentconnector.user.entity.User;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
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
public class Comment
{
	@Id
	@GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "comments_id_gen" )
	@SequenceGenerator( name = "comments_id_gen", sequenceName = "comments_id_seq", allocationSize = 1 )
	@Column( name = "id", nullable = false )
	private Long id;

	@ManyToOne( fetch = FetchType.LAZY )
	@JoinColumn( name = "parent_id", nullable = true )
	private Comment parent;

	@OneToMany( mappedBy = "parentComment", cascade = CascadeType.ALL, orphanRemoval = true )
	private List< Comment > replies = new ArrayList<>();

	@ManyToOne( fetch = FetchType.LAZY, optional = false )
	@JoinColumn( name = "author_id", nullable = false )
	private User author;

	@ManyToOne
	@Nullable
	@JoinTable(
			name = "patent_comments",
			joinColumns = @JoinColumn( name = "comment_id" ),
			inverseJoinColumns = @JoinColumn( name = "patent_id" )
	)
	private Patent patent;

	@ManyToOne
	@Nullable
	@JoinTable(
			name = "event_comments",
			joinColumns = @JoinColumn( name = "comment_id" ),
			inverseJoinColumns = @JoinColumn( name = "event_id" )
	)
	private Event event;

	@Column( name = "type", nullable = false, length = 20 )
	private CommentType type;

	@Column( name = "content", nullable = false, length = Integer.MAX_VALUE )
	private String content;

	@Column( name = "created_at" )
	private Instant createdAt;

	@Column( name = "updated_at" )
	private Instant updatedAt;

	@Column( name = "created_by", length = 50 )
	private String createdBy;

	@Column( name = "updated_by", length = 50 )
	private String updatedBy;

	@Column( name = "deleted_on" )
	private Instant deletedOn;

	@Column( name = "deleted_by", length = 100 )
	private String deletedBy;

}