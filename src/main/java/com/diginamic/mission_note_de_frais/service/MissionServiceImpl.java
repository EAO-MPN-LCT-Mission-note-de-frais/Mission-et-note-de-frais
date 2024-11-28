package com.diginamic.mission_note_de_frais.service;

import com.diginamic.mission_note_de_frais.model.repository.MissionRepository;
import com.diginamic.mission_note_de_frais.model.repository.StatusRepository;
import com.diginamic.mission_note_de_frais.model.repository.TransportRepository;
import com.diginamic.mission_note_de_frais.model.dto.MissionDTO;
import com.diginamic.mission_note_de_frais.model.dto.TransportDTO;
import com.diginamic.mission_note_de_frais.model.mapper.MissionMapper;
import com.diginamic.mission_note_de_frais.model.entity.Mission;
import com.diginamic.mission_note_de_frais.model.entity.Status.MissionStatus;
import com.diginamic.mission_note_de_frais.model.entity.Transport;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MissionServiceImpl implements MissionService {
    private final MissionRepository missionRepository;
    private final TransportRepository transportRepository;
    private final StatusRepository statusRepository;
    private final MissionMapper mapper;

    public MissionServiceImpl(
            MissionRepository missionRepository,
            TransportRepository transportRepository,
            StatusRepository statusRepository,
            MissionMapper mapper
    ) {
        this.missionRepository = missionRepository;
        this.transportRepository = transportRepository;
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
  
  @Override
  public MissionDTO addTransportToMission(Integer missionId, Long transportId) {
      Mission mission = missionRepository.findById(missionId)
              .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Mission not found"));

      Transport transport = transportRepository.findById(transportId)
              .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Transport not found"));

      mission.getTransports().add(transport);
      missionRepository.save(mission);

      return mapper.apply(mission);
  }

  @Override
  public MissionDTO removeTransportFromMission(Integer missionId, Long transportId) {
      Mission mission = missionRepository.findById(missionId)
              .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Mission not found"));

      Transport transport = transportRepository.findById(transportId)
              .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Transport not found"));

      mission.getTransports().remove(transport);
      missionRepository.save(mission);

      return mapper.apply(mission);
  }

  @Override
  public Set<TransportDTO> getTransportsForMission(Integer missionId) {
      Mission mission = missionRepository.findById(missionId)
              .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Mission not found"));

      return mission.getTransports().stream()
              .map(transport -> new TransportDTO(transport.getId(), transport.getName()))
              .collect(Collectors.toSet());
  }
}
