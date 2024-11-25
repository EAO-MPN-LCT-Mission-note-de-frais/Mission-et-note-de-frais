package com.diginamic.mission_note_de_frais.service;

import java.util.List;

import com.diginamic.mission_note_de_frais.model.dto.MissionTypeDTO;

/**
 * Interface définissant les services liés à la gestion des natures de mission.
 * <p>
 * Cette interface fournit des méthodes pour manipuler les données de nature de
 * mission, notamment leur récupération, création, mise à jour et suppression.
 */
public interface MissionTypeService {

	/**
	 * Récupère toutes les natures de mission disponibles.
	 * <p>
	 * Cette méthode retourne une liste de DTOs représentant les natures de mission
	 * enregistrées dans le système.
	 * 
	 * @return une liste de {@link MissionTypeDTO}, ou une liste vide si aucune
	 *         nature n'est disponible.
	 */
	List<MissionTypeDTO> getAllMissionTypes();

	/**
	 * Crée une nouvelle nature de mission.
	 * <p>
	 * Cette méthode permet d'ajouter une nouvelle nature de mission en utilisant un
	 * objet DTO fourni.
	 * 
	 * @param missionTypeDTO l'objet contenant les informations nécessaires pour
	 *                       créer la nature de mission.
	 * @return un {@link MissionTypeDTO} représentant la nature de mission créée.
	 * @throws IllegalArgumentException si les données fournies dans le DTO sont
	 *                                  invalides.
	 */
	MissionTypeDTO createMissionType(MissionTypeDTO missionTypeDTO);

	/**
	 * Met à jour une nature de mission existante.
	 * <p>
	 * Cette méthode permet de modifier une nature de mission en fonction de son
	 * identifiant unique et des nouvelles informations fournies via un DTO.
	 * 
	 * @param id             l'identifiant unique de la nature de mission à mettre à
	 *                       jour.
	 * @param missionTypeDTO un objet contenant les informations mises à jour.
	 * @return un {@link MissionTypeDTO} représentant la nature de mission mise à
	 *         jour.
	 * @throws ResourceNotFoundException si aucune nature de mission avec
	 *                                   l'identifiant donné n'est trouvée.
	 */
	MissionTypeDTO updateMissionType(Long id, MissionTypeDTO missionTypeDTO);

	/**
	 * Supprime une nature de mission.
	 * <p>
	 * Cette méthode permet de supprimer une nature de mission en fonction de son
	 * identifiant unique.
	 * 
	 * @param id l'identifiant unique de la nature de mission à supprimer.
	 * @throws ResourceNotFoundException si aucune nature de mission avec
	 *                                   l'identifiant donné n'est trouvée.
	 */
	void deleteMissionType(Long id);
}
