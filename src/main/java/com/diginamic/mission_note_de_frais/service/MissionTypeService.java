package com.diginamic.mission_note_de_frais.service;

import java.util.List;

import com.diginamic.mission_note_de_frais.model.dto.MissionTypeDTO;

public interface MissionTypeService {

	List<MissionTypeDTO> getAllMissionTypes();

	MissionTypeDTO createMissionType(MissionTypeDTO missionTypeDTO);

	MissionTypeDTO updateMissionType(Long id, MissionTypeDTO missionTypeDTO);

	void deleteMissionType(Long id);
}
