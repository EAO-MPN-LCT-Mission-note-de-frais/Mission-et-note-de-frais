package com.diginamic.mission_note_de_frais.service;

import com.diginamic.mission_note_de_frais.model.dto.MissionDTO;
import com.diginamic.mission_note_de_frais.model.dto.TransportDTO;

import java.util.List;
import java.util.Set;

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

    /**
     * Add a transport to a mission.
     *
     * @param missionId  the identifier of the mission
     * @param transportId the identifier of the transport
     * @return the updated {@link MissionDTO}
     */
    MissionDTO addTransportToMission(Integer missionId, Long transportId);

    /**
     * Remove a transport from a mission.
     *
     * @param missionId  the identifier of the mission
     * @param transportId the identifier of the transport
     * @return the updated {@link MissionDTO}
     */
    MissionDTO removeTransportFromMission(Integer missionId, Long transportId);

    /**
     * Get transports associated with a mission.
     *
     * @param missionId the identifier of the mission
     * @return a set of {@link TransportDTO} associated with the mission
     */
    Set<TransportDTO> getTransportsForMission(Integer missionId);
}
