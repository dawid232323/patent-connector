package com.amadon.patentconnector.patent.service;

import com.amadon.patentconnector.patent.entity.Patent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatentRepository extends JpaRepository< Patent, Long >
{
}
