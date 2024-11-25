package com.diginamic.mission_note_de_frais.model.repository;

import com.diginamic.mission_note_de_frais.model.entity.Status;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for performing database operations on a {@link Status}.
 */
@Repository
public interface StatusRepository extends ListCrudRepository<Status, Integer> {
  Optional<Status> findByName(Status.MissionStatus name);
}
