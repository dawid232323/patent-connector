package com.amadon.patentconnector.inventionDemand.service;

import com.amadon.patentconnector.businessBranch.entity.BusinessBranch;
import com.amadon.patentconnector.businessBranch.service.BusinessBranchService;
import com.amadon.patentconnector.inventionDemand.entity.InventionDemand;
import com.amadon.patentconnector.inventionDemand.service.dto.InventionDemandDto;
import com.amadon.patentconnector.inventionDemand.service.repository.InventionDemandRepository;
import com.amadon.patentconnector.user.entity.User;
import com.amadon.patentconnector.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class InventionDemandService
{
	private final InventionDemandRepository demandRepository;
	private final BusinessBranchService businessBranchService;
	private final UserService userService;

	@Transactional
	public void createInventionDemand( final InventionDemandDto aInventionDemandDto )
	{
		final InventionDemand inventionDemand = initDemand( aInventionDemandDto );
		demandRepository.save( inventionDemand );
		sendInventionDemandEmail( inventionDemand );
	}

	private InventionDemand initDemand( final InventionDemandDto aInventionDemandDto )
	{
		final User issuer = userService.getUserById( aInventionDemandDto.getIssuerId() );
		final User recipient = userService.getUserById( aInventionDemandDto.getRecipientId() );
		final Set< BusinessBranch > businessBranches = businessBranchService
				.getBusinessBranchesByIdIn( aInventionDemandDto.getBusinessBranchesIds() )
				.stream().collect( Collectors.toSet() );
		return InventionDemand.builder()
				.demandContent( aInventionDemandDto.getContent() )
				.issuer( issuer )
				.recipient( recipient )
				.businessBranches( businessBranches )
				.build();
	}

	// TODO create email send
	private void sendInventionDemandEmail( final InventionDemand inventionDemand )
	{

	}
}
