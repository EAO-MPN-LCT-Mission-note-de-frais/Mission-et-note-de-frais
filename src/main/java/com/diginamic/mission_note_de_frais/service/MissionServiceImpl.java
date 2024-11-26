package com.diginamic.mission_note_de_frais.service;

import com.diginamic.mission_note_de_frais.model.repository.MissionRepository;
import com.diginamic.mission_note_de_frais.model.repository.StatusRepository;
import com.diginamic.mission_note_de_frais.model.dto.MissionDTO;
import com.diginamic.mission_note_de_frais.model.mapper.MissionMapper;
import com.diginamic.mission_note_de_frais.model.entity.Mission;
import com.diginamic.mission_note_de_frais.model.entity.Status.MissionStatus;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class MissionServiceImpl implements MissionService {
  private final MissionRepository missionRepository;
  private final StatusRepository statusRepository;
  private final MissionMapper mapper;

  public MissionServiceImpl(
      MissionRepository missionRepository,
      StatusRepository statusRepository,
      MissionMapper mapper
  ) {
    this.missionRepository = missionRepository;
    this.statusRepository = statusRepository;
    this.mapper = mapper;
  }

  @Override
  public MissionDTO createMission(MissionDTO missionDto) {
    // Fetch the initial status for the mission
    var status = statusRepository.findByName(MissionStatus.INITIALE).orElseThrow(
        () -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Status not found")
    );

    // Create the mission entity from the DTO
    var entity = new Mission();
    entity.setStartDate(missionDto.getStartDate());
    entity.setEndDate(missionDto.getEndDate());
    entity.setStartTown(missionDto.getStartTown());
    entity.setEndTown(missionDto.getEndTown());
    entity.setStatus(status);


    // Save and return the entity as a MapperDto
    return missionRepository.save(entity).map(mapper);
  }

  @Override
  public List<MissionDTO> getMissions() {
    return missionRepository.findAll()
        .stream()
        .map(mapper).toList();
  }

  @Override
  public MissionDTO getMissionById(Integer missionId) {
    return missionRepository.findById(missionId)
        .map(mapper)
        .orElseThrow(() ->
            new ResponseStatusException(HttpStatus.NOT_FOUND, "Mission not found")
        );
  }

  @Override
  public MissionDTO updateMission(MissionDTO missionDto) {
    var entity = missionRepository.findById(missionDto.getId())
        .orElseThrow(() ->
            new ResponseStatusException(HttpStatus.NOT_FOUND, "Mission not found")
        );

    // Update the mission entity fields
    entity.setStartDate(missionDto.getStartDate());
    entity.setEndDate(missionDto.getEndDate());
    entity.setStartTown(missionDto.getStartTown());
    entity.setEndTown(missionDto.getEndTown());

    // Save and return the entity as a MapperDto
    return missionRepository.save(entity).map(mapper);
  }

  @Override
  public void deleteMission(Integer missionId) {
    // Delete the mission entity by its identifier
    // If the entity is not found, do nothing
    missionRepository.findById(missionId)
        .ifPresent(entity -> missionRepository.delete(entity));
  }
}
