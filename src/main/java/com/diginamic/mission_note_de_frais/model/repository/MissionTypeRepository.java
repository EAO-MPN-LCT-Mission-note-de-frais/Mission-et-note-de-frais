package com.diginamic.mission_note_de_frais.model.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.diginamic.mission_note_de_frais.model.entity.MissionType;

/**
 * Interface de gestion des opérations de persistence pour l'entité
 * {@link MissionType}.
 */
public interface MissionTypeRepository extends JpaRepository<MissionType, Long> {

    /**
     * Récupère toutes les natures de mission dont la date de fin est nulle.
     *
     * @return une liste de {@link MissionType} actives, ou une liste vide si aucune
     * nature active n'est trouvée.
     */
    List<MissionType> findByEndDateIsNull();

    /**
     * Récupère une nature de mission par son libellé, uniquement si elle est
     * active.
     *
     * @param label le libellé de la nature de mission recherchée.
     * @return un {@link Optional} contenant la nature de mission si elle est
     * trouvée et active, ou un {@link Optional#empty()} si aucune nature
     * correspondante n'est trouvée.
     */
    Optional<MissionType> findByLabelAndEndDateIsNull(String label);

    /**
     * Récupère les natures de mission contenant au moins une mission associée.
     *
     * @return une liste des natures de mission contenant des missions associées.
     */
    List<MissionType> findByMissionsIsNotEmpty();
}
