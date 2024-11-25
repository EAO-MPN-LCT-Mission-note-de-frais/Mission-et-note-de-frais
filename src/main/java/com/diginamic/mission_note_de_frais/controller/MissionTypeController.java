package com.diginamic.mission_note_de_frais.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.diginamic.mission_note_de_frais.model.dto.MissionTypeDTO;
import com.diginamic.mission_note_de_frais.service.MissionTypeService;

/**
 * Contrôleur REST pour gérer les natures de missions. Fournit des endpoints
 * pour récupérer, créer, modifier et supprimer les natures de missions.
 */
@RestController
@RequestMapping("/mission-types")
public class MissionTypeController {

	@Autowired
	private MissionTypeService missionTypeService;

	/**
	 * Récupère tous les natures de missions disponibles.
	 *
	 * @return une liste de {@link MissionTypeDTO} représentant les natures de
	 *         missions
	 */
	@GetMapping
	public List<MissionTypeDTO> getAllMissionTypes() {
		return missionTypeService.getAllMissionTypes();
	}

	/**
	 * Crée une nouvelle nature de mission.
	 *
	 * @param missionTypeDTO les données de la nature de mission à créer
	 * @return un {@link MissionTypeDTO} représentant la nature de mission créée
	 */
	@PostMapping
	public MissionTypeDTO createMissionType(@RequestBody MissionTypeDTO missionTypeDTO) {
		return missionTypeService.createMissionType(missionTypeDTO);
	}

	/**
	 * Met à jour une nature de mission existant.
	 *
	 * @param id             l'identifiant de la natures de mission à mettre à jour
	 * @param missionTypeDTO les nouvelles données de la nature de mission
	 * @return un {@link MissionTypeDTO} représentant la nature de mission mise à
	 *         jour
	 */
	@PutMapping("/{id}")
	public MissionTypeDTO updateMissionType(@PathVariable Long id, @RequestBody MissionTypeDTO missionTypeDTO) {
		return missionTypeService.updateMissionType(id, missionTypeDTO);
	}

	/**
	 * Supprime une nature de mission existante.
	 *
	 * @param id l'identifiant de la nature de mission à supprimer
	 */
	@DeleteMapping("/{id}")
	public void deleteMissionType(@PathVariable Long id) {
		missionTypeService.deleteMissionType(id);
	}
}
