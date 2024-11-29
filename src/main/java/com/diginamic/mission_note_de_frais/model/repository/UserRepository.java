package com.diginamic.mission_note_de_frais.model.repository;

import com.diginamic.mission_note_de_frais.model.entity.User;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;


public interface UserRepository extends ListCrudRepository<User, Long> {
  Optional<User> findByEmail(String email);
}
