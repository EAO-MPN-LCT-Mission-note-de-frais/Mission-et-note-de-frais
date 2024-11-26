package com.diginamic.mission_note_de_frais.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.diginamic.mission_note_de_frais.model.MissionType;

public interface MissionTypeRepository extends JpaRepository<MissionType, Long> {
	
	List<MissionType> findByEndDateIsNull(); // Natures actives

    Optional<MissionType> findByLabelAndEndDateIsNull(String label);

}
