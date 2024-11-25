package com.diginamic.mission_note_de_frais.service;

import com.diginamic.mission_note_de_frais.model.dto.MissionDto;

import java.util.List;

/**
 * Service class for managing missions.
 */
public interface MissionService {
  /**
   * Create a new mission.
   */
  MissionDto createMission(MissionDto missionDto);

  /**
   * Get an existing mission.
   */
  List<MissionDto> getMissions();

  /**
   * Get an existing mission by its identifier.
   *
   * @param missionId the identifier of the mission
   */
  MissionDto getMissionById(Integer missionId);

  /**
   * Update an existing mission.
   *
   * @param missionDto an object containing information about the mission
   */
  MissionDto updateMission(MissionDto missionDto);

  /**
   * Delete an existing mission.
   *
   * @param missionId the identifier of the mission
   */
  void deleteMission(Integer missionId);
}
