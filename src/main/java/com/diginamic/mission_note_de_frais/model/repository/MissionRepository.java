package com.diginamic.mission_note_de_frais.model.repository;

import com.diginamic.mission_note_de_frais.model.entity.Mission;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repository for performing database operations on a {@link Mission}.
 */
@Repository
public interface MissionRepository extends ListCrudRepository<Mission, Integer> {

    /**
     * Récupère les missions associées à un transport donné.
     *
     * @param transportId l'identifiant du transport
     * @return une liste des missions associées
     */
    List<Mission> findByTransports_Id(Long transportId);
}
