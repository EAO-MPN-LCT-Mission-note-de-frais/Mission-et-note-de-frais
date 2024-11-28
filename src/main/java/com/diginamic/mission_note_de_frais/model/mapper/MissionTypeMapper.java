package com.diginamic.mission_note_de_frais.model.mapper;

import com.diginamic.mission_note_de_frais.model.dto.MissionTypeDTO;
import com.diginamic.mission_note_de_frais.model.entity.MissionType;
import org.springframework.stereotype.Component;

/**
 * Mapper pour convertir un {@link MissionType} en {@link MissionTypeDTO} et vice-versa.
 */
@Component
public class MissionTypeMapper {

    /**
     * Convertit une entité {@link MissionType} en DTO {@link MissionTypeDTO}.
     *
     * @param missionType l'entité à convertir
     * @return le DTO correspondant
     */
    public MissionTypeDTO toDTO(MissionType missionType) {
        if (missionType == null) {
            return null;
        }

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

    /**
     * Convertit un DTO {@link MissionTypeDTO} en entité {@link MissionType}.
     *
     * @param missionTypeDTO le DTO à convertir
     * @return l'entité correspondante
     */
    public MissionType toEntity(MissionTypeDTO missionTypeDTO) {
        if (missionTypeDTO == null) {
            return null;
        }

        MissionType missionType = new MissionType();
        missionType.setLabel(missionTypeDTO.getLabel());
        missionType.setIsCharged(missionTypeDTO.getIsCharged());
        missionType.setIsBonus(missionTypeDTO.getIsBonus());
        missionType.setAverageDailyRate(missionTypeDTO.getAverageDailyRate());
        missionType.setBonusPercentage(missionTypeDTO.getBonusPercentage());
        missionType.setStartDate(missionTypeDTO.getStartDate());
        missionType.setEndDate(missionTypeDTO.getEndDate());
        return missionType;
    }
}
