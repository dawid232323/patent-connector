package com.amadon.patentconnector.event.service;

import com.amadon.patentconnector.businessBranch.entity.BusinessBranch;
import com.amadon.patentconnector.businessBranch.service.BusinessBranchService;
import com.amadon.patentconnector.event.entity.Event;
import com.amadon.patentconnector.event.service.dto.EventDto;
import com.amadon.patentconnector.event.service.dto.EventSearchQueryDto;
import com.amadon.patentconnector.event.service.dto.PersistEventDto;
import com.amadon.patentconnector.event.service.mapper.EventMapper;
import com.amadon.patentconnector.event.service.repository.EventRepository;
import com.amadon.patentconnector.shared.service.specification.SpecificationProvider;
import com.amadon.patentconnector.user.entity.User;
import com.amadon.patentconnector.user.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventService
{
	private final EventRepository eventRepository;
	private final EventMapper eventMapper;
	private final UserService userService;
	private final BusinessBranchService businessBranchService;
	private final SpecificationProvider< EventSearchQueryDto, Event > specificationProvider;

	public EventDto create( final PersistEventDto aEventDto )
	{
		log.info( "Creating event {}", aEventDto );
		final Event createdEvent = eventMapper.toEntity( aEventDto );
		setBusinessBranches( createdEvent, aEventDto.getBusinessBranchesIds() );
		setCaregiver( createdEvent );
		eventRepository.save( createdEvent );
		log.info( "Event {} created and saved", createdEvent.getTitle() );
		return eventMapper.toDto( createdEvent );
	}

	public EventDto update( final PersistEventDto aEventDto, final Long aExistingId )
	{
		log.info( "Updating event {}", aEventDto );
		Event persistedEvent = getEvent( aExistingId );
		persistedEvent = eventMapper.partialUpdate( aEventDto, persistedEvent );
		setBusinessBranches( persistedEvent, aEventDto.getBusinessBranchesIds() );
		setCaregiver( persistedEvent );
		eventRepository.save( persistedEvent );
		log.info( "Event {} updated and saved", persistedEvent.getTitle() );
		return eventMapper.toDto( persistedEvent );
	}

	public EventDto get( final Long aExistingId )
	{
		return eventMapper.toDto( getEvent( aExistingId ) );
	}

	public Page< EventDto > searchEvents( final EventSearchQueryDto aSearchQueryDto, final Pageable pageable )
	{
		final Specification< Event > specification = specificationProvider.getSpecification( aSearchQueryDto );
		return eventRepository.findAll( specification, pageable ).map( eventMapper::toDto );
	}

	public void deleteEvent( final Long aEventId )
	{
		log.info( "Deleting event {}", aEventId );
		final User user = userService.getLoggedUser();
		final Event event = getEvent( aEventId );
		if ( Objects.nonNull( event.getDeletedOn() ) ) {
			log.warn( "Event {} already deleted by {}. Skipping.", aEventId, event.getDeletedOn() );
			return;
		}
		event.setDeletedBy( user.getUsername() );
		event.setDeletedOn( LocalDateTime.now() );
		eventRepository.save( event );
		log.info( "Event {} deleted by {}", aEventId, user.getUsername() );
	}

	private Event getEvent( final Long aExistingId )
	{
		return eventRepository.findById( aExistingId )
				.orElseThrow( () -> new EntityNotFoundException( "Event with id " + aExistingId + " not found or deleted" ) );
	}

	private void setBusinessBranches( final Event aEvent, final List< Long > businessBranchesIds )
	{
		final List< BusinessBranch > businessBranches =
				businessBranchService.getBusinessBranchesByIdIn( businessBranchesIds );
		aEvent.getBusinessBranches()
				.clear();
		aEvent.getBusinessBranches()
				.addAll( businessBranches );
	}

	private void setCaregiver( final Event aEvent )
	{
		final User loggedUser = userService.getLoggedUser();
		aEvent.setCaregiver( loggedUser );
	}
}
