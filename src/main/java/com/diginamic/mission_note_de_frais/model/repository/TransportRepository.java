package com.diginamic.mission_note_de_frais.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.diginamic.mission_note_de_frais.model.entity.Transport;
import java.util.List;

/**
 * Repository pour accéder aux données des moyens de transport dans la base de
 * données.
 * <p>
 * Ce repository étend l'interface {@link JpaRepository} de Spring Data JPA, ce
 * qui permet d'utiliser les méthodes de persistance de données standard telles
 * que {@link JpaRepository#save(Object)},
 * {@link JpaRepository#findById(Object)}, et {@link JpaRepository#findAll()}.
 */
public interface TransportRepository extends JpaRepository<Transport, Long> {

    /**
     * Vérifie si un moyen de transport avec le nom spécifié existe déjà dans la
     * base de données.
     *
     * @param name le nom du transport à vérifier
     * @return true si un transport avec ce nom existe, sinon false
     */
    boolean existsByName(String name);

    /**
     * Récupère les transports associés à une mission donnée.
     *
     * @param missionId l'identifiant de la mission
     * @return une liste des transports associés
     */
    List<Transport> findByMissions_Id(Integer missionId);
}
