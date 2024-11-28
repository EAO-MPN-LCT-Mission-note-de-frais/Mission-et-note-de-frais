package com.diginamic.mission_note_de_frais.service;

import com.diginamic.mission_note_de_frais.model.dto.TransportDTO;
import com.diginamic.mission_note_de_frais.model.entity.Transport;

import java.util.List;

/**
 * Interface qui définit les services pour gérer les moyens de transport.
 * <p>
 * Cette interface fournit les méthodes nécessaires pour récupérer, créer,
 * mettre à jour et supprimer des moyens de transport dans l'application.
 * Les méthodes utilisent des objets de type {@link TransportDTO} pour le transfert
 * des données.
 */
public interface TransportService {

    /**
     * Récupère tous les moyens de transport.
     *
     * @return une liste de {@link TransportDTO} représentant tous les moyens de
     *         transport
     */
    List<TransportDTO> getAllTransports();

    /**
     * Récupère un moyen de transport par son identifiant.
     *
     * @param id L'identifiant unique du transport à récupérer.
     * @return L'entité `Transport` correspondant à l'identifiant.
     */
    Transport getTransportById(Long id);

    /**
     * Crée un nouveau moyen de transport.
     * <p>
     * Cette méthode permet de créer un moyen de transport en vérifiant qu'aucun
     * transport avec le même nom n'existe déjà.
     *
     * @param transportDTO les données du transport à créer
     * @return un {@link TransportDTO} représentant le transport créé
     * @throws IllegalArgumentException si un transport avec le même nom existe déjà
     */
    TransportDTO createTransport(TransportDTO transportDTO);

    /**
     * Met à jour un moyen de transport existant.
     * <p>
     * Cette méthode permet de mettre à jour les données d'un transport existant, en
     * vérifiant que le transport à mettre à jour existe.
     *
     * @param id           l'identifiant du transport à mettre à jour
     * @param transportDTO les nouvelles données du transport
     * @return un {@link TransportDTO} représentant le transport mis à jour
     * @throws IllegalArgumentException si le transport n'existe pas
     */
    TransportDTO updateTransport(Long id, TransportDTO transportDTO);

    /**
     * Supprime un moyen de transport.
     * <p>
     * Cette méthode permet de supprimer un transport existant en vérifiant que le
     * transport à supprimer existe.
     *
     * @param id l'identifiant du transport à supprimer
     * @throws IllegalArgumentException si le transport n'existe pas
     */
    void deleteTransport(Long id);

}
