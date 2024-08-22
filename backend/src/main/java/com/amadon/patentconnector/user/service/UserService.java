package com.amadon.patentconnector.user.service;

import com.amadon.patentconnector.security.service.SecurityService;
import com.amadon.patentconnector.user.entity.User;
import com.amadon.patentconnector.user.service.dto.UserDto;
import com.amadon.patentconnector.user.service.mapper.UserMapper;
import com.amadon.patentconnector.user.service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService
{
	private final UserRepository userRepository;
	private final SecurityService securityService;
	private final UserMapper userMapper;

	public Optional< User > tryToFindByEmail( final String aEmail )
	{
		return userRepository.findByEmail( aEmail );
	}

	@Override
	public UserDetails loadUserByUsername( final String aUsername ) throws UsernameNotFoundException
	{
		final Optional< User > optionalUser = tryToFindByEmail( aUsername );
		if ( optionalUser.isEmpty() )
		{
			throw new UsernameNotFoundException( "Couldn't find user with username " + aUsername );
		}
		return optionalUser.get();
	}

	public User getLoggedUser()
	{
		final String email = securityService.getAuthenticatedUserEmail();
		if ( Objects.isNull( email ) )
		{
			throw new SecurityException();
		}
		return tryToFindByEmail( email ).orElseThrow( () -> new UsernameNotFoundException( "Couldn't find user with " +
																								   "username " + email ) );
	}

	public UserDto getLoggedUserDto()
	{
		return userMapper.toDto( getLoggedUser() );
	}
}
