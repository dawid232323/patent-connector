package com.amadon.patentconnector.inventionDemand.service.repository;

import com.amadon.patentconnector.inventionDemand.entity.InventionDemand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventionDemandRepository extends JpaRepository< InventionDemand, Long >
{
}
