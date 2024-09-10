package com.amadon.patentconnector.user.service;

import com.amadon.patentconnector.businessBranch.entity.BusinessBranch;
import com.amadon.patentconnector.businessBranch.service.BusinessBranchService;
import com.amadon.patentconnector.security.service.SecurityService;
import com.amadon.patentconnector.user.entity.User;
import com.amadon.patentconnector.user.entity.UserRole;
import com.amadon.patentconnector.user.service.dto.UserDto;
import com.amadon.patentconnector.user.service.exception.RoleMismatchException;
import com.amadon.patentconnector.user.service.exception.UserNotFoundException;
import com.amadon.patentconnector.user.service.mapper.UserMapper;
import com.amadon.patentconnector.user.service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService
{
	private final UserRepository userRepository;
	private final SecurityService securityService;
	private final UserMapper userMapper;
	private final BusinessBranchService businessBranchService;

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

	public UserDto updateUserBusinessBranches( final List< Long > aBusinessBranchesIds, final Long aUserId )
	{
		final User userToUpdate = userRepository.findById( aUserId )
				.orElseThrow( () -> new UserNotFoundException( aUserId ) );
		checkIfUserHasDesiredRoles( userToUpdate, UserRole.ENTREPRENEUR );
		final Set< BusinessBranch > businessBranches =
				Set.copyOf( businessBranchService.getBusinessBranchesByIdIn( aBusinessBranchesIds ) );
		userToUpdate.getEntrepreneursData()
				.setInterestedBusinessBranches( businessBranches );
		return userMapper.toDto( userRepository.save( userToUpdate ) );
	}

	private void checkIfUserHasDesiredRoles( final User aUser, final UserRole... aUserRoles )
	{
		final Set< UserRole > userRoles = Set.copyOf( aUser.getAuthorities() );
		for ( UserRole role : aUserRoles )
		{
			if ( !userRoles.contains( role ) )
			{
				throw new RoleMismatchException( aUser.getEmail(), role );
			}
		}
	}
}
