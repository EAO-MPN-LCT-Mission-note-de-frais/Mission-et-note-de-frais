package com.diginamic.mission_note_de_frais.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diginamic.mission_note_de_frais.model.dto.MissionTypeDTO;
import com.diginamic.mission_note_de_frais.model.entity.MissionType;
import com.diginamic.mission_note_de_frais.model.repository.MissionTypeRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class MissionTypeServiceImpl implements MissionTypeService {

    @Autowired
    private MissionTypeRepository missionTypeRepository;

    @Override
    public List<MissionTypeDTO> getAllMissionTypes() {
        return missionTypeRepository.findAll().stream()
            .map(this::mapToDTO)
            .toList();
    }

    @Override
    public MissionTypeDTO createMissionType(MissionTypeDTO missionTypeDTO) {
        if (missionTypeRepository.findByLabelAndEndDateIsNull(missionTypeDTO.getLabel()).isPresent()) {
            throw new IllegalArgumentException("Une nature active avec ce libellé existe déjà.");
        }
        MissionType missionType = mapToEntity(missionTypeDTO);
        missionType.setStartDate(LocalDate.now());
        missionTypeRepository.save(missionType);
        return mapToDTO(missionType);
    }

    @Override
    public MissionTypeDTO updateMissionType(Long id, MissionTypeDTO missionTypeDTO) {
        MissionType missionType = missionTypeRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Nature de mission non trouvée"));

        if (missionType.getEndDate() == null) {
            missionType.setEndDate(LocalDate.now());
            missionTypeRepository.save(missionType);

            MissionType newMissionType = mapToEntity(missionTypeDTO);
            newMissionType.setStartDate(LocalDate.now().plusDays(1));
            missionTypeRepository.save(newMissionType);

            return mapToDTO(newMissionType);
        } else {
            throw new IllegalArgumentException("Impossible de modifier une nature expirée.");
        }
    }

    @Override
    public void deleteMissionType(Long id) {
        MissionType missionType = missionTypeRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Nature de mission non trouvée"));

        if (missionType.getEndDate() == null) {
            missionType.setEndDate(LocalDate.now());
            missionTypeRepository.save(missionType);
        } else {
            missionTypeRepository.delete(missionType);
        }
    }

    private MissionTypeDTO mapToDTO(MissionType missionType) {
        return new MissionTypeDTO(
            missionType.getId(),
            missionType.getLabel(),
            missionType.getIsCharged(),
            missionType.getIsBonus(),
            missionType.getAverageDailyRate(),
            missionType.getBonusPercentage(),
            missionType.getStartDate(),
            missionType.getEndDate()
        );
    }

    private MissionType mapToEntity(MissionTypeDTO missionTypeDTO) {
        MissionType missionType = new MissionType();
        missionType.setLabel(missionTypeDTO.getLabel());
        missionType.setIsCharged(missionTypeDTO.getIsCharged());
        missionType.setIsBonus(missionTypeDTO.getIsBonus());
        missionType.setAverageDailyRate(missionTypeDTO.getAverageDailyRate());
        missionType.setBonusPercentage(missionTypeDTO.getBonusPercentage());
        return missionType;
    }
}

