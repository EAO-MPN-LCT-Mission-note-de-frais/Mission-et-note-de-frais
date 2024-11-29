package com.diginamic.mission_note_de_frais.service;

import com.diginamic.mission_note_de_frais.model.repository.MissionRepository;
import com.diginamic.mission_note_de_frais.model.repository.StatusRepository;
import com.diginamic.mission_note_de_frais.model.repository.TransportRepository;
import com.diginamic.mission_note_de_frais.model.dto.MissionDTO;
import com.diginamic.mission_note_de_frais.model.dto.TransportDTO;
import com.diginamic.mission_note_de_frais.model.mapper.MissionMapper;
import com.diginamic.mission_note_de_frais.model.entity.Mission;
import com.diginamic.mission_note_de_frais.model.entity.Transport;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.diginamic.mission_note_de_frais.model.entity.Status.MissionStatus.EN_ATTENTE_VALIDATION;
import static com.diginamic.mission_note_de_frais.model.entity.Status.MissionStatus.INITIALE;
import static com.diginamic.mission_note_de_frais.model.entity.Status.MissionStatus.REJETEE;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

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
        validateMissionDates(missionDto.getStartDate(), missionDto.getEndDate());

        // Fetch the initial status for the mission
        var status = statusRepository.findByName(INITIALE).orElseThrow(
                () -> new ResponseStatusException(INTERNAL_SERVER_ERROR, "'INITIALE' status not found")
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

    /**
     * Validate the dates of a mission.
     *
     * @param startDate the date the mission starts
     * @param endDate   the date the mission ends
     */
    private void validateMissionDates(LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Start date must be before end date");
        }

        // A mission cannot start in the past or start today
        if (startDate.isBefore(LocalDate.now()) || startDate.isEqual(LocalDate.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Start date cannot be in the past or today");
        }
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
        var mission = missionRepository.findById(missionDto.getId())
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Mission with id " + missionDto.getId() + " not found")
                );

        if (!canUpdateMission(mission)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Mission with 'INITIALE' or 'REJETEE' status cannot be updated");
        }

        // Fetch the initial status for the mission
        var initialStatus = statusRepository.findByName(INITIALE).orElseThrow(
                () -> new ResponseStatusException(INTERNAL_SERVER_ERROR, "'INITIALE' status not found")
        );
        // Change the status to INITIALE when updating
        mission.setStatus(initialStatus);

        // Update the mission entity fields
        mission.setStartDate(missionDto.getStartDate());
        mission.setEndDate(missionDto.getEndDate());
        mission.setStartTown(missionDto.getStartTown());
        mission.setEndTown(missionDto.getEndTown());

        // Save and return the entity as a MapperDto
        return missionRepository.save(mission).map(mapper);
    }

    @Override
    public void deleteMission(Integer missionId) {
        // Fetch the mission entity by its identifier
        var mission = missionRepository.findById(missionId)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Mission with id " + missionId + " not found")
                );

        if (!canDeleteMission(mission)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "The mission has ended and cannot be deleted");
        }

        // Delete the mission entity
        missionRepository.delete(mission);
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

    @Override
    public MissionDTO validateMission(Integer missionId) {
        var mission = missionRepository.findById(missionId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Mission not found"));

        // Check if the mission can be validated
        if (!canValidateMission(mission)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "The mission cannot be validated. Status: " + mission.getStatus().getName());
        }

        var status = statusRepository.findByName(EN_ATTENTE_VALIDATION)
                .orElseThrow(() -> new ResponseStatusException(INTERNAL_SERVER_ERROR, "'EN_ATTENTE_VALIDATION' status not found"));

        mission.setStatus(status);
        return missionRepository.save(mission).map(mapper);
    }

    /**
     * Check if a mission can be validated.
     * A mission can be validated if its status is 'EN_ATTENTE_VALIDATION'.
     *
     * @param mission the mission to check
     * @return true if the mission can be validated, false otherwise
     */
    private boolean canValidateMission(Mission mission) {
        return mission.getStatus().getName().equals(EN_ATTENTE_VALIDATION);
    }

    /**
     * Check if a mission can be updated.
     * A mission can be updated if its status is 'INITIALE' or 'REJETEE'.
     *
     * @param mission the mission to check
     * @return true if the mission can be updated, false otherwise
     */
    private static boolean canUpdateMission(Mission mission) {
        var status = Optional.ofNullable(mission.getStatus())
                .orElseThrow(
                        () -> new ResponseStatusException(INTERNAL_SERVER_ERROR, "Invalid mission status: " + mission.getStatus())
                );

        return status.getName().equals(INITIALE) || status.getName().equals(REJETEE);
    }

    /**
     * Check if a mission can be deleted.
     * A mission can be deleted if the mission has ended.
     *
     * @param mission the mission to check
     * @return true if the mission can be deleted, false otherwise
     */
    private static boolean canDeleteMission(Mission mission) {
        return mission.getEndDate().isBefore(LocalDate.now());
    }
}
