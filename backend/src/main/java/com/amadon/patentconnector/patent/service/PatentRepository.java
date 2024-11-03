package com.amadon.patentconnector.patent.service;

import com.amadon.patentconnector.patent.entity.Patent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PatentRepository extends JpaRepository< Patent, Long >, JpaSpecificationExecutor< Patent >
{
}
