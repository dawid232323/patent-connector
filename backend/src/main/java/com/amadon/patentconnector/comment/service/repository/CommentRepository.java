package com.amadon.patentconnector.comment.service.repository;

import com.amadon.patentconnector.comment.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository< Comment, Long >, JpaSpecificationExecutor< Comment >
{
	@Query( value = """
				select distinct c from Comment c
					left join fetch c.replies
						where c.parent is null
							and c.deletedOn is null
							and c.type = 'PATENT'
										and c.patent.id = :patentId
			""",
			countQuery = """
					select count(c) from Comment c
					where c.parent is null and c.type = 'PATENT' and c.patent.id = :patentId and c.deletedOn is null
					""" )
	Page< Comment > findAllPatentComments( @Param( "patentId" ) final Long patentId, final Pageable pageable );

	@Query( value = """
				select distinct c from Comment c
					left join fetch c.replies
						where c.parent is null
							and c.deletedOn is null
							and c.type = 'EVENT'
										and c.event.id = :eventId
			""",
			countQuery = """
					select count(c) from Comment c
					where c.parent is null and c.type = 'EVENT' and c.event.id = :eventId and c.deletedOn is null
					""" )
	Page< Comment > findAllEventComments( @Param( "eventId" ) final Long eventId, final Pageable pageable );

	@Modifying
	@Query( "update Comment c set c.deletedOn = CURRENT TIMESTAMP, c.deletedBy = :username where c.id = :commentId or c.parent.id = :commentId" )
	void markAsDeletedByPatentId( @Param( "commentId" ) Long commentId, @Param( "username" ) String aUsername );
}
