package com.diginamic.mission_note_de_frais.controller;

import com.diginamic.mission_note_de_frais.model.dto.MissionDto;
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
  public MissionDto createMission(
      @RequestBody MissionDto missionDto
  ) {
    // Create a new mission
    return missionService.createMission(missionDto);
  }

  @GetMapping("/missions")
  public List<MissionDto> getMissions() {
    // Get an existing mission
    return missionService.getMissions();
  }

  @GetMapping("/missions/{id}")
  public MissionDto getMissionById(@PathVariable() Integer id) {
    // Get an existing mission
    return missionService.getMissionById(id);
  }

  @PutMapping("/missions")
  public MissionDto updateMission(@RequestBody MissionDto missionDto) {
    // Update an existing mission
    return missionService.updateMission(missionDto);
  }

  @DeleteMapping("/missions/{id}")
  public void deleteMission(@PathVariable() Integer id) {
    // Delete an existing mission
    missionService.deleteMission(id);
  }
}
