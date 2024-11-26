package com.diginamic.mission_note_de_frais.service;

import com.diginamic.mission_note_de_frais.model.dto.MissionDTO;

import java.util.List;

/**
 * Service class for managing missions.
 */
public interface MissionService {
  /**
   * Create a new mission.
   */
  MissionDTO createMission(MissionDTO missionDto);

  /**
   * Get an existing mission.
   */
  List<MissionDTO> getMissions();

  /**
   * Get an existing mission by its identifier.
   *
   * @param missionId the identifier of the mission
   */
  MissionDTO getMissionById(Integer missionId);

  /**
   * Update an existing mission.
   *
   * @param missionDto an object containing information about the mission
   */
  MissionDTO updateMission(MissionDTO missionDto);

  /**
   * Delete an existing mission.
   *
   * @param missionId the identifier of the mission
   */
  void deleteMission(Integer missionId);
}
