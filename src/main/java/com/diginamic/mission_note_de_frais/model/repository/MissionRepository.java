package com.diginamic.mission_note_de_frais.model.repository;

import com.diginamic.mission_note_de_frais.model.entity.Mission;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for performing database operations on a {@link Mission}.
 */
@Repository
public interface MissionRepository extends ListCrudRepository<Mission, Integer> { }
