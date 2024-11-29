package com.diginamic.mission_note_de_frais.model.repository;

import com.diginamic.mission_note_de_frais.model.entity.Credential;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Repository for performing database operations on a {@link Credential}.
 */
public interface CredentialRepository extends CrudRepository<Credential, Long> {
  /**
   * Retrieves a credential by user id.
   *
   * @param userId The id of the user the credential belongs to.
   * @return A credential object or {@literal Optional#empty()} if none found.
   */
  Optional<Credential> findByUserId(Long userId);
}
