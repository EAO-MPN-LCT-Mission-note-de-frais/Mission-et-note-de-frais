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

@RestController
@RequestMapping("/mission-types")
public class MissionTypeController {

    @Autowired
    private MissionTypeService missionTypeService;

    @GetMapping
    public List<MissionTypeDTO> getAllMissionTypes() {
        return missionTypeService.getAllMissionTypes();
    }

    @PostMapping
    public MissionTypeDTO createMissionType(@RequestBody MissionTypeDTO missionTypeDTO) {
        return missionTypeService.createMissionType(missionTypeDTO);
    }

    @PutMapping("/{id}")
    public MissionTypeDTO updateMissionType(@PathVariable Long id, @RequestBody MissionTypeDTO missionTypeDTO) {
        return missionTypeService.updateMissionType(id, missionTypeDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteMissionType(@PathVariable Long id) {
        missionTypeService.deleteMissionType(id);
    }
}