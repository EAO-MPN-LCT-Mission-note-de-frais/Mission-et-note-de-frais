package com.diginamic.mission_note_de_frais.controller;

import com.diginamic.mission_note_de_frais.model.dto.MissionDTO;
import com.diginamic.mission_note_de_frais.model.dto.TransportDTO;
import com.diginamic.mission_note_de_frais.service.MissionService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MissionController {

  private final MissionService missionService;

  public MissionController(MissionService missionService) {
    this.missionService = missionService;
  }

  @PostMapping("/missions")
  public MissionDTO createMission(
      @RequestBody MissionDTO missionDto
  ) {
    // Create a new mission
    return missionService.createMission(missionDto);
  }

  @GetMapping("/missions")
  public List<MissionDTO> getMissions() {
    // Get an existing mission
    return missionService.getMissions();
  }

  @GetMapping("/missions/{id}")
  public MissionDTO getMissionById(@PathVariable() Integer id) {
    // Get an existing mission
    return missionService.getMissionById(id);
  }

  @PutMapping("/missions")
  public MissionDTO updateMission(@RequestBody MissionDTO missionDto) {
    // Update an existing mission
    return missionService.updateMission(missionDto);
  }

  @DeleteMapping("/missions/{id}")
  public void deleteMission(@PathVariable() Integer id) {
    // Delete an existing mission
    missionService.deleteMission(id);
  }
  
  @PostMapping("/missions/{missionId}/transports/{transportId}")
  public MissionDTO addTransportToMission(
          @PathVariable Integer missionId,
          @PathVariable Long transportId
  ) {
      return missionService.addTransportToMission(missionId, transportId);
  }
  
  @DeleteMapping("/missions/{missionId}/transports/{transportId}")
  public MissionDTO removeTransportFromMission(
          @PathVariable Integer missionId,
          @PathVariable Long transportId
  ) {
      return missionService.removeTransportFromMission(missionId, transportId);
  }
  
  @GetMapping("/missions/{missionId}/transports")
  public List<TransportDTO> getTransportsForMission(@PathVariable Integer missionId) {
      return missionService.getTransportsForMission(missionId).stream().toList();
  }
}
