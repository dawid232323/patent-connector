package com.amadon.patentconnector.user.service;

import com.amadon.patentconnector.user.entity.User;
import com.amadon.patentconnector.user.service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService
{
	private final UserRepository userRepository;

	public Optional< User > tryToFindByEmail( final String aEmail )
	{
		return userRepository.findByEmail( aEmail );
	}
}
