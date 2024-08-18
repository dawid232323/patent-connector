package com.amadon.patentconnector.user.service;

import com.amadon.patentconnector.user.entity.User;
import com.amadon.patentconnector.user.service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
class UserPersistenceService
{
	private final UserRepository userRepository;

	public User save( final User aUser )
	{
		final User savedUser = userRepository.save( aUser );
		log.info( "Saved user {} with id {}", savedUser.getEmail(), savedUser.getId() );
		return savedUser;
	}
}
