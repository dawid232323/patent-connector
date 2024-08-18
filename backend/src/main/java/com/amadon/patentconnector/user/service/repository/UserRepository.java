package com.amadon.patentconnector.user.service.repository;

import com.amadon.patentconnector.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository< User, Long >
{
	Optional< User > findByEmail( String aEmail );
}
