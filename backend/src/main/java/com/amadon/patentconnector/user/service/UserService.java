package com.amadon.patentconnector.user.service;

import com.amadon.patentconnector.user.entity.User;
import com.amadon.patentconnector.user.service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService
{
	private final UserRepository userRepository;

	public Optional< User > tryToFindByEmail( final String aEmail )
	{
		return userRepository.findByEmail( aEmail );
	}

	@Override
	public UserDetails loadUserByUsername( final String aUsername ) throws UsernameNotFoundException
	{
		final Optional<User> optionalUser = tryToFindByEmail( aUsername );
		if ( optionalUser.isEmpty() )
		{
			throw new UsernameNotFoundException( "Couldn't find user with username " + aUsername );
		}
		return optionalUser.get();
	}
}
